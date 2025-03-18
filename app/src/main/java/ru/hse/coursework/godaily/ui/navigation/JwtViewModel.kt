package ru.hse.coursework.godaily.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.security.JwtManager
import javax.inject.Inject

@HiltViewModel
class JwtViewModel @Inject constructor(
    private val jwtManager: JwtManager
) : ViewModel() {

    val jwtFlow = jwtManager.jwtFlow

    fun saveJwtToStorage(jwt: String) {
        viewModelScope.launch {
            jwtManager.saveJwt(jwt)
        }
    }

    fun clearJwt() {
        viewModelScope.launch {
            jwtManager.clearJwt()
        }
    }

    //TODO
    fun validateJwt(jwt: String?): Boolean {
        return jwt?.isNotEmpty() == true
    }
}


