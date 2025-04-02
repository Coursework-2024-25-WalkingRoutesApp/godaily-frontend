package ru.hse.coursework.godaily.core.security

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val _jwtFlow = MutableStateFlow<String?>(sharedPreferences.getString(JWT_KEY, null))
    val jwtFlow: StateFlow<String?> = _jwtFlow

    fun saveJwt(jwt: String) {
        sharedPreferences.edit().putString(JWT_KEY, jwt).apply()
        _jwtFlow.value = jwt
    }

    fun clearJwt() {
        sharedPreferences.edit().remove(JWT_KEY).apply()
        _jwtFlow.value = null
    }

    companion object {
        private const val JWT_KEY = "jwt_key"
    }
}
