package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchRouteReviewsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: String): RouteReviews {

        val reviews = api.getReviews("", routeId)

        val rating = if (reviews.isNotEmpty()) {
            reviews.sumOf { it.mark } / reviews.size.toDouble()
        } else {
            0.toDouble()
        }

        return RouteReviews(
            routes = reviews,
            rating = rating,
            reviewsCount = reviews.size
        )

    }
}

data class RouteReviews(
    val routes: List<ReviewDTO>,
    val rating: Double,
    val reviewsCount: Int
)