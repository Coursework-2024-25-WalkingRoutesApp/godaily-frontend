package ru.hse.coursework.godaily.core.security

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val _jwtFlow = MutableStateFlow(sharedPreferences.getString(JWT_KEY, null))
    val jwtFlow: StateFlow<String?> = _jwtFlow

    private val _verificationFlow =
        MutableStateFlow(sharedPreferences.getBoolean(VERIFICATION_KEY, false))
    val verificationFlow: StateFlow<Boolean> = _verificationFlow

    fun saveJwt(jwt: String) {
        sharedPreferences.edit().putString(JWT_KEY, jwt).apply()
        _jwtFlow.value = jwt
    }

    fun clearJwt() {
        sharedPreferences.edit().remove(JWT_KEY).apply()
        _jwtFlow.value = null
    }

    fun saveVerificationStatus(status: Boolean) {
        sharedPreferences.edit().putBoolean(VERIFICATION_KEY, status).apply()
        _verificationFlow.value = status
    }

    fun clearVerificationStatus() {
        sharedPreferences.edit().remove(VERIFICATION_KEY).apply()
        _verificationFlow.value = false
    }

    companion object {
        private const val JWT_KEY = "jwt_key"
        private const val VERIFICATION_KEY = "verification_key"
    }
}
