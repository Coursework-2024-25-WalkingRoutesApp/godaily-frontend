package ru.hse.coursework.godaily.screen.routedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteReviewsUseCase
import javax.inject.Inject

@HiltViewModel
class RouteReviewsViewModel @Inject constructor(
    private val fetchRouteReviewsUseCase: FetchRouteReviewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteReviewsUiState())
    val uiState: StateFlow<RouteReviewsUiState> = _uiState

    fun loadRouteReviews(routeId: String) {
        viewModelScope.launch {
            val routeReviews = fetchRouteReviewsUseCase.execute(routeId)
            _uiState.value = RouteReviewsUiState(
                curUserReview = routeReviews.curUserReview,
                reviews = routeReviews.routes,
                rating = routeReviews.rating,
                reviewsCount = routeReviews.reviewsCount
            )
        }
    }
}

data class RouteReviewsUiState(
    val curUserReview: ReviewDTO? = null,
    val reviews: List<ReviewDTO> = listOf(),
    val rating: Double = 0.toDouble(),
    val reviewsCount: Int = 0,
)
