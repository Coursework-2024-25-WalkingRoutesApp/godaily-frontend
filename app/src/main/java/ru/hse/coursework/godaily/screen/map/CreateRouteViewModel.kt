package ru.hse.coursework.godaily.screen.map

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.TitledPoint
import ru.hse.coursework.godaily.core.domain.routes.SaveRouteUseCase
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateRouteViewModel @Inject constructor(
    private val saveRouteUseCase: SaveRouteUseCase,
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
    private val uuidService: UuidService
) : ViewModel() {

    val routeIdState: MutableState<UUID> = mutableStateOf(UUID.randomUUID())
    val routeTitle: MutableState<String> = mutableStateOf("")
    val routeDescription: MutableState<String> = mutableStateOf("")
    val startPoint: MutableState<String> = mutableStateOf("")
    val endPoint: MutableState<String> = mutableStateOf("")
    val routePoints = mutableStateListOf<TitledPoint>()
    val chosenCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val showPublishWarningDialog: MutableState<Boolean> = mutableStateOf(false)
    val showNewPublishDialog: MutableState<Boolean> = mutableStateOf(false)
    val showAddPointTitleDialog: MutableState<Boolean> = mutableStateOf(false)

    val isDataLoaded = mutableStateOf(false)

    fun clear() {
        routeIdState.value = UUID.randomUUID()
        routeTitle.value = ""
        routeDescription.value = ""
        startPoint.value = ""
        endPoint.value = ""
        routePoints.clear()
        chosenCategories.value = setOf()
        selectedImageUri.value = null
        showPublishWarningDialog.value = false
        showNewPublishDialog.value = false
        showAddPointTitleDialog.value = false
        isDataLoaded.value = false
    }

    suspend fun loadRouteData(routeId: String?) {
        if (isDataLoaded.value) return

        val routeIdUUID = routeId?.let { uuidService.getUUIDFromString(routeId) }
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val route = fetchRouteDetailsUseCase.execute(routeIdUUID)

                routeIdState.value = route.route.id
                routeTitle.value = route.route.routeName ?: ""
                routeDescription.value = route.route.description ?: ""
                startPoint.value = route.route.startPoint ?: ""
                endPoint.value = route.route.endPoint ?: ""

                routePoints.clear()
                routePoints.addAll(route.routePoints)

                //TODO: работа с категориями
                //TODO: работа с картинкой
            }
        } else {
            //TODO логика не смогли загрузить или нет маршрута
        }
        isDataLoaded.value = true
    }


    fun updateRouteTitle(routeTitleValue: String) {
        routeTitle.value = routeTitleValue
    }

    fun saveRouteToDrafts() {
        saveRoute()
    }

    fun publishRoute(context: Context) {
        if (routePoints.size < 2) {
            ToastManager(context).showToast("Слишком мало точек на маршруте (<2)")
            return
        } else if (routeTitle.value.isEmpty() || startPoint.value.isEmpty() ||
            endPoint.value.isEmpty()
        ) {
            ToastManager(context).showToast("Не все необходимые поля заполнены")
            return
        } else if (selectedImageUri.value == null) {
            ToastManager(context).showToast("Не выбрано фото маршрута")
            return
        }
        saveRoute()
    }

    private fun saveRoute() {
        viewModelScope.launch {
            saveRouteUseCase.execute(
                id = routeIdState.value,
                routeName = routeTitle.value,
                description = routeDescription.value,
                startPoint = startPoint.value,
                endPoint = endPoint.value,
                imageUri = selectedImageUri.value,
                isDraft = false,
                routePoints = routePoints,
                categories = chosenCategories.value
            )
        }
    }
}
