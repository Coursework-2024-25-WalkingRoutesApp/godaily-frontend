package ru.hse.coursework.godaily.screen.routedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import java.util.UUID
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
    val route: RoutePageDto = RoutePageDto(
        UUID.randomUUID(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        listOf(),
        listOf()
    ),
    val mark: Double = 0.toDouble(),
    val reviewsCount: Int = 0
)
