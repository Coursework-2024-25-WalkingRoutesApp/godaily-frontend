package ru.hse.coursework.godaily.screen.profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.profile.FetchProfileInfoUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserEditedNameUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserPhotoUseCase
import ru.hse.coursework.godaily.core.security.JwtManager
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileInfoUseCase: FetchProfileInfoUseCase,
    private val saveUserEditedNameUseCase: SaveUserEditedNameUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val jwtManager: JwtManager
) : ViewModel() {

    val email: MutableState<String> = mutableStateOf("email")
    val userName: MutableState<String> = mutableStateOf("Ваше имя")
    val profilePictureUrl: MutableState<String> =
        mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG3RRs0ouk80nISkSjBII8TgTshOBcitVnJg&s")
    val completedRoutes = mutableStateListOf<RouteCardDto>()
    val favouriteRoutes = mutableStateListOf<RouteCardDto>()

    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val editedUserName = mutableStateOf("Ваше имя")
    //TODO решить, какие тут еще штуки можно менять в редактировании

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val profile = fetchProfileInfoUseCase.execute()
            email.value = profile.email
            userName.value = profile.username
            editedUserName.value = profile.username
            profilePictureUrl.value = profile.photoUrl ?: ""

            completedRoutes.clear()
            completedRoutes.addAll(profile.completedRoutes)

            favouriteRoutes.clear()
            favouriteRoutes.addAll(profile.favouriteRoutes)
        }
    }

    fun saveNewUserData(context: Context) {
        viewModelScope.launch {
            editedUserName.value.takeIf { it.isNotEmpty() }?.let {
                val nameResult = saveUserEditedNameUseCase.execute(it)
                ToastManager(context).showToast(nameResult.message())
            }

            selectedImageUri.value?.let {
                val photoResult = saveUserPhotoUseCase.execute(it)
                ToastManager(context).showToast(photoResult.message())
            }
        }
    }

    fun exit() {
        jwtManager.clearJwt()
    }
}

