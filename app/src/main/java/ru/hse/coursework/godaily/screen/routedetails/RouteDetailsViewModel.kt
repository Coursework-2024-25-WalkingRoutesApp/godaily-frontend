package ru.hse.coursework.godaily.screen.routedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteDetailsUiState())
    val uiState: StateFlow<RouteDetailsUiState> = _uiState

    fun loadRouteDetails(routeId: String) {
        viewModelScope.launch {
            val routeDetails = fetchRouteDetailsUseCase.execute(routeId)
            _uiState.value = RouteDetailsUiState(
                route = routeDetails.route,
                mark = routeDetails.mark,
                reviewsCount = routeDetails.reviewsCount
            )
        }
    }
}

data class RouteDetailsUiState(
    val route: RoutePageDTO = RoutePageDTO(
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        "",
        false,
        listOf(),
        listOf()
    ),
    val mark: Double = 0.toDouble(),
    val reviewsCount: Int = 0
)
