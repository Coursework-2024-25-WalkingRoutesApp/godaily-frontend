package ru.hse.coursework.godaily.screen.map

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
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RouteDetails
import ru.hse.coursework.godaily.core.domain.routes.SaveRouteUseCase
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.core.domain.service.CropRoutePreviewService
import ru.hse.coursework.godaily.core.domain.service.PhotoConverterService
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateRouteViewModel @Inject constructor(
    private val saveRouteUseCase: SaveRouteUseCase,
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
    private val uuidService: UuidService,
    private val photoConverterService: PhotoConverterService,
    private val errorHandler: ErrorHandler,
    val imageLoader: ImageLoader,
    val cropRoutePreviewService: CropRoutePreviewService,
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    val routeIdState: MutableState<UUID?> = mutableStateOf(null)
    val routeTitle: MutableState<String> = mutableStateOf("")
    val routeDescription: MutableState<String> = mutableStateOf("")
    val startPoint: MutableState<String> = mutableStateOf("")
    val endPoint: MutableState<String> = mutableStateOf("")
    val routePoints = mutableStateListOf<TitledPoint>()
    val chosenCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val routePreviewUrl: MutableState<String?> = mutableStateOf(null)
    val showPublishWarningDialog: MutableState<Boolean> = mutableStateOf(false)
    val showNewPublishDialog: MutableState<Boolean> = mutableStateOf(false)
    val showAddPointTitleDialog: MutableState<Boolean> = mutableStateOf(false)
    val showUnsuccessfulPublishDialog: MutableState<Boolean> = mutableStateOf(false)
    val showTutorial: MutableState<Boolean> = mutableStateOf(false)

    val isDataLoaded = mutableStateOf(false)

    fun clear() {
        routeIdState.value = null
        routeTitle.value = ""
        routeDescription.value = ""
        startPoint.value = ""
        endPoint.value = ""
        routePoints.clear()
        chosenCategories.value = setOf()
        selectedImageUri.value = null
        routePreviewUrl.value = null
        showPublishWarningDialog.value = false
        showNewPublishDialog.value = false
        showAddPointTitleDialog.value = false
        showTutorial.value = false
        isDataLoaded.value = false
    }

    suspend fun loadRouteData(routeId: String?) {
        if (isDataLoaded.value) return

        val routeIdUUID = routeId?.let { uuidService.getUUIDFromString(routeId) }
        if (routeIdUUID != null) {
            viewModelScope.launch {
                isLoading.value = true
                val routeResponse = fetchRouteDetailsUseCase.execute(routeIdUUID)

                when (routeResponse) {
                    is ApiCallResult.Error -> errorHandler.handleError(routeResponse)
                    is ApiCallResult.Success -> {
                        if (routeResponse.data is RouteDetails) {
                            routeIdState.value = routeResponse.data.route.id
                            routeTitle.value = routeResponse.data.route.routeName ?: ""
                            routeDescription.value = routeResponse.data.route.description ?: ""
                            startPoint.value = routeResponse.data.route.startPoint ?: ""
                            endPoint.value = routeResponse.data.route.endPoint ?: ""

                            routePoints.clear()
                            routePoints.addAll(routeResponse.data.routePoints)

                            routeResponse.data.route.categories?.let {
                                chosenCategories.value = convertCategories(it)
                            }

                            routePreviewUrl.value = routeResponse.data.route.routePreview

                            selectedImageUri.value =
                                photoConverterService.urlToUri(routeResponse.data.route.routePreview)
                        }
                    }
                }
                isLoading.value = false
            }
        }
        isDataLoaded.value = true
    }

    private fun convertCategories(categories: List<RouteDto.Category>): Set<Int> {
        val categoryMap = mapOf(
            "Природный" to 0,
            "Культурно-исторический" to 1,
            "Кафе по пути" to 2,
            "У метро" to 3
        )

        return categories.mapNotNull { categoryMap[it.categoryName] }.toSet()
    }

    suspend fun saveRouteToDrafts(context: Context): Boolean {
        isLoading.value = true
        val isSuccess = saveRoute(context, isDraft = true)
        if (isSuccess) {
            ToastManager(context).showToast("Маршрут успешно добавлен в черновики")
        } else {
            ToastManager(context).showToast("Ошибка при добавлении в черновики")
        }
        isLoading.value = false
        return isSuccess
    }

    suspend fun publishRoute(context: Context): Boolean {
        if (routePoints.size < 2) {
            ToastManager(context).showToast("Слишком мало точек на маршруте (<2)")
            return false
        }
        if (routeTitle.value.isEmpty() || startPoint.value.isEmpty() || endPoint.value.isEmpty()) {
            ToastManager(context).showToast("Не все необходимые поля заполнены")
            return false
        }
        if (selectedImageUri.value == null) {
            ToastManager(context).showToast("Не выбрано фото маршрута")
            return false
        }

        isLoading.value = true
        val isSuccess = saveRoute(context, isDraft = false)
        isLoading.value = false
        if (!isSuccess) {
            ToastManager(context).showToast("Ошибка при публикации маршрута")
        }
        return isSuccess
    }

    private suspend fun saveRoute(context: Context, isDraft: Boolean): Boolean {
        isLoading.value = true
        val resultResponse = saveRouteUseCase.execute(
            id = routeIdState.value,
            routeName = routeTitle.value,
            description = routeDescription.value,
            startPoint = startPoint.value,
            endPoint = endPoint.value,
            imageUri = selectedImageUri.value,
            isDraft = isDraft,
            routePoints = routePoints,
            categories = chosenCategories.value,
            photoUrl = routePreviewUrl.value
        )
        isLoading.value = false

        return when (resultResponse) {
            is ApiCallResult.Error -> {
                errorHandler.handleError(resultResponse)
                false
            }

            is ApiCallResult.Success -> {
                true
            }
        }
    }


}
