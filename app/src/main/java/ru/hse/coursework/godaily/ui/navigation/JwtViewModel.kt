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
import ru.hse.coursework.godaily.core.domain.authorization.CheckJwtUseCase
import ru.hse.coursework.godaily.core.security.JwtManager
import javax.inject.Inject

@HiltViewModel
class JwtViewModel @Inject constructor(
    private val checkJwtUseCase: CheckJwtUseCase,
    private val jwtManager: JwtManager
) : ViewModel() {
    private val _isTokenValid = MutableStateFlow<Boolean?>(null)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid.asStateFlow()

    val jwtFlow = jwtManager.jwtFlow

    init {
        viewModelScope.launch {
            jwtFlow.collect { jwt ->
                jwt?.let { checkTokenValidity() }
                    ?: _isTokenValid.update { false }
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
                    jwtManager.clearJwt()
                    false
                }
            }
        }
        _isTokenValid.update { isValid }
    }
}


