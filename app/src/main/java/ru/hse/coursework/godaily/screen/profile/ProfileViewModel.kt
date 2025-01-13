package ru.hse.coursework.godaily.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.profile.FetchProfileInfoUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileInfoUseCase: FetchProfileInfoUseCase
) : ViewModel() {

    // Состояние UI экрана
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val profile = fetchProfileInfoUseCase.execute("")
            _uiState.value = _uiState.value.copy(
                userName = profile.name,
                profilePicture = R.drawable.default_profile_photo,//profile.photoUrl,
                completedRoutesCount = profile.completedRoutes.count(),
                favouriteRoutesCount = profile.favouriteRoutes.count()
            )
        }
    }

    fun onEditProfileClicked() {
        // Обработка перехода к редактированию профиля
    }

    fun onCompletedRoutesClicked() {
        // Обработка перехода к экрану "Пройденные маршруты"
    }

    fun onFavouriteRoutesClicked() {
        // Обработка перехода к экрану "Избранное"
    }
}

data class ProfileUiState(
    val userName: String = "Ваше имя",
    val profilePicture: Int = R.drawable.default_profile_photo,
    val completedRoutesCount: Int = 0,
    val favouriteRoutesCount: Int = 0
)

