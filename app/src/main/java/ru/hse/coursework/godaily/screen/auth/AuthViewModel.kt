package ru.hse.coursework.godaily.screen.auth

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.authorization.CheckEmailVerificationUseCase
import ru.hse.coursework.godaily.core.domain.authorization.CheckVerificationCodeUseCase
import ru.hse.coursework.godaily.core.domain.authorization.LoginUserUseCase
import ru.hse.coursework.godaily.core.domain.authorization.RegisterUserUseCase
import ru.hse.coursework.godaily.core.domain.authorization.SendVerificationCodeUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserPhotoUseCase
import ru.hse.coursework.godaily.core.security.VerificationManager
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val verificationManager: VerificationManager,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
    private val checkVerificationCodeUseCase: CheckVerificationCodeUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordAgain = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val code = mutableStateListOf("", "", "", "", "", "")
    val jwt = mutableStateOf("")
    val verificationStatus = mutableStateOf(false)

    val wasSent = mutableStateOf(false)


    fun clear() {
        password.value = ""
        passwordAgain.value = ""

        jwt.value = ""
        verificationStatus.value = false

        code.clear()
        code.addAll(listOf("", "", "", "", "", ""))
    }

    suspend fun registerUser(): Boolean {
        isLoading.value = true

        if (isValidEmail(email.value) &&
            isValidPassword(password.value, passwordAgain.value) &&
            isValidUsername(username.value)
        ) {
            val resultResponse = registerUserUseCase.execute(
                email.value, password.value, username.value
            )
            isLoading.value = false
            return when (resultResponse) {
                is ApiCallResult.Error -> {
                    errorHandler.handleError(resultResponse)
                    false
                }

                is ApiCallResult.Success -> {
                    jwt.value = resultResponse.data
                    saveJwtToStorage()
                    true
                }
            }
        }
        isLoading.value = false
        return false
    }

    fun saveJwtToStorage() {
        viewModelScope.launch {
            verificationManager.saveJwt(jwt.value)
        }
    }

    fun saveVerificationStatusToStorage() {
        viewModelScope.launch {
            verificationManager.saveVerificationStatus(verificationStatus.value)
        }
    }

    suspend fun checkVerification(): Boolean {
        isLoading.value = true
        return try {
            when (val resultResponse = checkEmailVerificationUseCase.execute()) {
                is ApiCallResult.Error -> {
                    errorHandler.handleError(resultResponse)
                    false
                }

                is ApiCallResult.Success -> {
                    val result = resultResponse.data
                    withContext(Dispatchers.Main) {
                        verificationStatus.value = result
                        saveVerificationStatusToStorage()
                    }
                    result
                }
            }
        } finally {
            withContext(Dispatchers.Main) {
                isLoading.value = false
            }
        }
    }

    suspend fun loginUser(): Boolean {
        isLoading.value = true
        if (isValidEmail(email.value) &&
            isPasswordLongEnough(password.value)
        ) {
            val resultResponse = loginUserUseCase.execute(
                email.value, password.value
            )
            isLoading.value = false
            return when (resultResponse) {
                is ApiCallResult.Error -> {
                    errorHandler.handleError(resultResponse)
                    false
                }

                is ApiCallResult.Success -> {
                    jwt.value = resultResponse.data
                    saveJwtToStorage()
                    true
                }
            }
        }
        isLoading.value = false
        return false
    }

    suspend fun sendCode(): Boolean {
        if (wasSent.value) return false

        return try {
            val responseResult = sendVerificationCodeUseCase.execute(email.value)

            when (responseResult) {
                is ApiCallResult.Success -> {
                    wasSent.value = true
                    true
                }

                is ApiCallResult.Error -> {
                    errorHandler.handleError(responseResult)
                    false
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun verifyUser(type: String): Boolean {
        isLoading.value = true
        return try {
            val verificationResult = checkVerificationCodeUseCase.execute(code.joinToString(""))
            when (verificationResult) {
                is ApiCallResult.Success -> {
                    val verificationStatusResult = checkEmailVerificationUseCase.execute()
                    when (verificationStatusResult) {
                        is ApiCallResult.Success -> {
                            if (verificationStatusResult.data) {
                                verificationStatus.value = true
                                if (type == "login") {
                                    saveVerificationStatusToStorage()
                                }
                                true
                            } else {
                                false
                            }
                        }

                        is ApiCallResult.Error -> {
                            errorHandler.handleError(verificationStatusResult)
                            false
                        }
                    }
                }

                is ApiCallResult.Error -> {
                    errorHandler.handleError(verificationResult)
                    false
                }
            }
        } finally {
            isLoading.value = false
        }
    }

    fun addProfilePhoto() {
        viewModelScope.launch {
            selectedImageUri.value?.let {
                val photoResultResponse = saveUserPhotoUseCase.execute(it, null)
                if (photoResultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(photoResultResponse)
                }
            }
        }
    }
}


private fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isPasswordLongEnough(password: String): Boolean {
    return password.length >= 8
}

private fun isValidPassword(password: String, passwordAgain: String): Boolean {
    return password.length >= 8 && (password == passwordAgain)
}

private fun isValidUsername(username: String): Boolean {
    return username.isNotEmpty()
}