package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.domain.routedetails.AddRouteToFavouritesUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RemoveRouteFromFavouritesUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
    private val addRouteToFavouritesUseCase: AddRouteToFavouritesUseCase,
    private val removeRouteFromFavouritesUseCase: RemoveRouteFromFavouritesUseCase
) : ViewModel() {

    val route: MutableState<RoutePageDto> = mutableStateOf(
        RoutePageDto(
            id = UUID.randomUUID(),
            routeName = null,
            description = null,
            duration = null,
            length = null,
            startPoint = null,
            endPoint = null,
            routePreview = null,
            isFavourite = false,
            routeCoordinate = null,
            categories = null
        )
    )
    val mark: MutableState<Double> = mutableStateOf(5.0)
    val reviewsCount: MutableState<Int> = mutableStateOf(0)
    val isFavourite: MutableState<Boolean> = mutableStateOf(false)

    fun loadRouteDetails(routeId: String) {
        viewModelScope.launch {
            val routeDetails = fetchRouteDetailsUseCase.execute(routeId)
            route.value = routeDetails.route
            mark.value = routeDetails.mark
            reviewsCount.value = routeDetails.reviewsCount
            isFavourite.value = route.value.isFavourite
        }
    }

    fun addRouteToFavourites() {
        viewModelScope.launch {
            addRouteToFavouritesUseCase.execute(route.value.id)
        }
    }

    fun removeRouteFromFavourites() {
        viewModelScope.launch {
            removeRouteFromFavouritesUseCase.execute(route.value.id)
        }
    }

    fun updateIsFavourite() {
        isFavourite.value = !isFavourite.value
    }
}
