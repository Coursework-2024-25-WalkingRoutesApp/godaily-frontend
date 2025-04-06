package ru.hse.coursework.godaily.screen.auth

import android.content.Context
import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.domain.authorization.LoginUserUseCase
import ru.hse.coursework.godaily.core.domain.authorization.RegisterUserUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserPhotoUseCase
import ru.hse.coursework.godaily.core.security.JwtManager
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val jwtManager: JwtManager,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
) : ViewModel() {

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordAgain = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val jwt = mutableStateOf("")

    fun clear() {
        password.value = ""
        passwordAgain.value = ""
    }

    suspend fun registerUser(): Boolean {
        if (isValidEmail(email.value) &&
            isValidPassword(password.value, passwordAgain.value) &&
            isValidUsername(username.value)
        ) {
            val result = registerUserUseCase.execute(
                email.value, password.value, username.value
            )
            if (result.isSuccessful) {
                jwt.value = result.body() ?: "no jwt"
            }
            return result.isSuccessful
        }
        return false
    }

    fun saveJwtToStorage() {
        viewModelScope.launch {
            jwtManager.saveJwt(jwt.value)
        }
    }

    //TODO
    suspend fun loginUser(): Boolean {
        if (isValidEmail(email.value) &&
            isPasswordLongEnough(password.value)
        ) {
            val result = loginUserUseCase.execute(
                email.value, password.value
            )
            if (result.isSuccessful) {
                jwtManager.saveJwt(result.body() ?: "no jwt")
            }
            return result.isSuccessful
        }
        return false
    }

    fun addProfilePhoto(context: Context) {
        viewModelScope.launch {
            selectedImageUri.value?.let {
                val photoResult = saveUserPhotoUseCase.execute(it)
                ToastManager(context).showToast(photoResult.message())
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


}