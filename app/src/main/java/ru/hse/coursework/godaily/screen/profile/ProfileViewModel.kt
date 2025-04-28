package ru.hse.coursework.godaily.screen.profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.profile.FetchProfileInfoUseCase
import ru.hse.coursework.godaily.core.domain.profile.ProfileInfo
import ru.hse.coursework.godaily.core.domain.profile.SaveUserEditedNameUseCase
import ru.hse.coursework.godaily.core.domain.profile.SaveUserPhotoUseCase
import ru.hse.coursework.godaily.core.security.VerificationManager
import ru.hse.coursework.godaily.core.tracking.TrackingService
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileInfoUseCase: FetchProfileInfoUseCase,
    private val saveUserEditedNameUseCase: SaveUserEditedNameUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val verificationManager: VerificationManager,
    private val errorHandler: ErrorHandler,
    private val trackingService: TrackingService,
    val imageLoader: ImageLoader
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    val email: MutableState<String> = mutableStateOf("email")
    val userName: MutableState<String> = mutableStateOf("Ваше имя")
    val profilePictureUrl: MutableState<String> = mutableStateOf("")
    val completedRoutes = mutableStateListOf<RouteCardDto>()
    val favouriteRoutes = mutableStateListOf<RouteCardDto>()

    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val editedUserName = mutableStateOf("Ваше имя")

    fun loadUserData() {
        viewModelScope.launch {
            isLoading.value = true
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
            isLoading.value = false
        }
    }

    fun saveNewUserData(context: Context, onComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true

            editedUserName.value.takeIf { it.isNotEmpty() }?.let {
                val nameResultResponse = saveUserEditedNameUseCase.execute(it)
                if (nameResultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(nameResultResponse)
                } else if (nameResultResponse is ApiCallResult.Success) {
                    userName.value = editedUserName.value
                }
            }

            selectedImageUri.value?.let { uri ->
                val photoResultResponse = saveUserPhotoUseCase.execute(
                    uri,
                    profilePictureUrl.value.takeIf { it.isNotEmpty() }
                )
                if (photoResultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(photoResultResponse)
                }
            }

            isLoading.value = false
            onComplete()
        }
    }


    fun trackRouteDetailsOpen(routeId: UUID?, routeName: String?) {
        trackingService.trackRouteDetailsOpen(routeId, routeName)
    }

    fun exit() {
        verificationManager.clearJwt()
        verificationManager.clearVerificationStatus()
    }
}

