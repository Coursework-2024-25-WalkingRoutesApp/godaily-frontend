package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.SaveReviewUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RateRouteViewModel @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
    private val saveReviewUseCase: SaveReviewUseCase
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
    val mark: MutableState<Int> = mutableStateOf(5)
    val reviewText: MutableState<String> = mutableStateOf("")

    fun updateRoute(routeValue: RoutePageDto) {
        route.value = routeValue
    }

    fun updateMark(markValue: Int) {
        mark.value = markValue
    }

    fun updateReviewText(reviewTextValue: String) {
        reviewText.value = reviewTextValue
    }

    fun loadRateReviewDetails(routeId: String, mark: Int) {
        viewModelScope.launch {
            val routeDetails = fetchRouteDetailsUseCase.execute(routeId)
            updateRoute(routeDetails.route)
            updateMark(mark)
        }
    }

    fun saveReview() {
        viewModelScope.launch {
            val response = saveReviewUseCase.execute(
                route.value.id,
                reviewText.value,
                mark.value
            )

            if (response.isSuccessful) {
                //TODO
            } else {
                //TODO
            }
        }
    }

}
