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
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.profile.FetchProfileInfoUseCase
import ru.hse.coursework.godaily.core.domain.profile.ProfileInfo
import ru.hse.coursework.godaily.core.domain.profile.SaveUserEditedNameUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserPhotoUseCase
import ru.hse.coursework.godaily.core.security.JwtManager
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileInfoUseCase: FetchProfileInfoUseCase,
    private val saveUserEditedNameUseCase: SaveUserEditedNameUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val jwtManager: JwtManager,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val email: MutableState<String> = mutableStateOf("email")
    val userName: MutableState<String> = mutableStateOf("Ваше имя")
    val profilePictureUrl: MutableState<String> = mutableStateOf("")
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
            val profileResponse = fetchProfileInfoUseCase.execute()

            when (profileResponse) {
                is ApiCallResult.Error -> errorHandler.handleError(profileResponse)
                is ApiCallResult.Success -> {
                    if (profileResponse.data is ProfileInfo) {
                        email.value = profileResponse.data.email
                        userName.value = profileResponse.data.username
                        editedUserName.value = profileResponse.data.username
                        profilePictureUrl.value = profileResponse.data.photoUrl ?: ""

                        completedRoutes.clear()
                        completedRoutes.addAll(profileResponse.data.completedRoutes)

                        favouriteRoutes.clear()
                        favouriteRoutes.addAll(profileResponse.data.favouriteRoutes)
                    }
                }
            }

        }
    }

    fun saveNewUserData(context: Context) {
        viewModelScope.launch {
            editedUserName.value.takeIf { it.isNotEmpty() }?.let {
                val nameResultResponse = saveUserEditedNameUseCase.execute(it)
                if (nameResultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(nameResultResponse)
                }
            }

            selectedImageUri.value?.let {
                val photoResultResponse = saveUserPhotoUseCase.execute(it)
                if (photoResultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(photoResultResponse)
                }
            }
        }
    }

    fun exit() {
        jwtManager.clearJwt()
    }
}

