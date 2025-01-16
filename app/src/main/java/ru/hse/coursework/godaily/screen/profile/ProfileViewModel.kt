package ru.hse.coursework.godaily.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.domain.profile.FetchProfileInfoUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileInfoUseCase: FetchProfileInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val profile = fetchProfileInfoUseCase.execute("")
            _uiState.value = _uiState.value.copy(
                email = profile.email,
                userName = profile.username,
                profilePictureUrl = profile.photoUrl,
                completedRoutes = profile.completedRoutes,
                favouriteRoutes = profile.favouriteRoutes
            )
        }
    }

    fun onEditProfileClicked() {
        // Обработка перехода к редактированию профиля
    }

    fun onFavouriteRoutesClicked() {
        // Обработка перехода к экрану "Избранное"
    }
}

data class ProfileUiState(
    val email: String = "email",
    val userName: String = "Ваше имя",
    val profilePictureUrl: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG3RRs0ouk80nISkSjBII8TgTshOBcitVnJg&s",
    val completedRoutes: List<RouteCardDTO> = listOf(),
    val favouriteRoutes: List<RouteCardDTO> = listOf()
)

