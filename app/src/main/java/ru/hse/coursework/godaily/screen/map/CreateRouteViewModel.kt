package ru.hse.coursework.godaily.screen.map

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routes.SaveRouteUseCase
import ru.hse.coursework.godaily.core.domain.service.UuidService
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
    val routePoints = mutableStateListOf<Point>()
    val chosenCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val showPublishWarningDialog: MutableState<Boolean> = mutableStateOf(false)
    val showNewPublishDialog: MutableState<Boolean> = mutableStateOf(false)

    suspend fun loadRouteData(routeId: String?) {
        val routeIdUUID = routeId?.let { uuidService.getUUIDFromString(routeId) }
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val route = fetchRouteDetailsUseCase.execute(routeIdUUID)

                routeIdState.value = route.route.id
                routeTitle.value = route.route.routeName ?: ""
                routeDescription.value = route.route.description ?: ""
                startPoint.value = route.route.startPoint ?: ""
                endPoint.value = route.route.endPoint ?: ""

                //TODO: апдейт Points
                //TODO: работа с категориями
                //TODO: работа с картинкой
            }
        } else {
            //TODO логика не смогли загрузить или нет маршрута
        }
    }


    fun updateRouteTitle(routeTitleValue: String) {
        routeTitle.value = routeTitleValue
    }

    fun saveRouteToDrafts() {
        //TODO
    }

    fun publishRoute() {
        //TODO проверки на то что все не null
        viewModelScope.launch {
            /*
            saveRouteUseCase.execute(
                id = routeIdState.value,
                routeName = routeTitle.value,
                description = routeDescription.value,
                startPoint = startPoint.value,
                endPoint = endPoint.value
                routePreview: String,
                isDraft = false,
                points: List<Point>,
                routeCoordinate: List<RouteDto.RouteCoordinate>,
                categories: List<RouteDto.Category>
            )*/
        }
    }
}
