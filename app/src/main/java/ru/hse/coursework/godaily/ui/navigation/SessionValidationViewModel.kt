package ru.hse.coursework.godaily.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.coursework.godaily.core.data.model.UserDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.authorization.CheckEmailVerificationUseCase
import ru.hse.coursework.godaily.core.domain.authorization.CheckJwtUseCase
import ru.hse.coursework.godaily.core.security.VerificationManager
import javax.inject.Inject

@HiltViewModel
class SessionValidationViewModel @Inject constructor(
    private val checkJwtUseCase: CheckJwtUseCase,
    private val checkEmailVerificationUseCase: CheckEmailVerificationUseCase,
    private val verificationManager: VerificationManager
) : ViewModel() {
    private val _isTokenValid = MutableStateFlow<Boolean?>(null)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

    private val _isVerified = MutableStateFlow<Boolean?>(null)
    val isVerified: StateFlow<Boolean?> = _isVerified.asStateFlow()

    val jwtFlow = verificationManager.jwtFlow
    val verificationFlow = verificationManager.verificationFlow

    init {
        viewModelScope.launch {
            launch {
                jwtFlow.collect { jwt ->
                    jwt?.let { checkTokenValidity() }
                        ?: _isTokenValid.update { false }
                }
            }
            launch {
                verificationFlow.collect {
                    checkVerification()
                }
            }
        }
    }

    private suspend fun checkTokenValidity() {
        val isValid = withContext(Dispatchers.IO) {
            val resultResponse = checkJwtUseCase.execute()
            when (resultResponse) {
                is ApiCallResult.Success -> {
                    val result = resultResponse.data is UserDto
                    result
                }

                is ApiCallResult.Error -> {
                    verificationManager.clearJwt()
                    false
                }
            }
        }
        _isTokenValid.update { isValid }
    }

    private suspend fun checkVerification() {
        val isVerified = withContext(Dispatchers.IO) {
            val resultResponse = checkEmailVerificationUseCase.execute()
            when (resultResponse) {
                is ApiCallResult.Success -> {
                    if (resultResponse.data) {
                        verificationManager.saveVerificationStatus(true)
                        true
                    } else {
                        verificationManager.clearVerificationStatus()
                        false
                    }

                }

                is ApiCallResult.Error -> {
                    verificationManager.clearVerificationStatus()
                    false
                }
            }
        }

        _isVerified.update { isVerified }
    }
}


