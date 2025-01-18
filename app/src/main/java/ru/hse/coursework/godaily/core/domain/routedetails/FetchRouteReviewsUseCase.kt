package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchRouteReviewsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: String): RouteReviewsInfo {

        val reviewsInfo = fetchReviewsInfo(routeId)
        val reviews = reviewsInfo.reviews

        val curUserReview = findCurrentUserReview(reviews, reviewsInfo.userId)
        val sortedReviews = sortReviews(reviews, curUserReview)
        val rating = calculateRating(reviews)

        return RouteReviewsInfo(
            curUserReview = curUserReview,
            routes = sortedReviews,
            rating = rating,
            reviewsCount = reviews.size
        )
    }

    private suspend fun fetchReviewsInfo(routeId: String) = api.getReviewsInfo("", routeId)

    private fun findCurrentUserReview(reviews: List<ReviewDTO>, userId: String): ReviewDTO? {
        return reviews.find { it.userId == userId }
    }

    private fun sortReviews(reviews: List<ReviewDTO>, curUserReview: ReviewDTO?): List<ReviewDTO> {
        return if (curUserReview != null) {
            listOf(curUserReview) + reviews.filter { it != curUserReview }.sortedByDescending { it.createdAt }
        } else {
            reviews.sortedByDescending { it.createdAt }
        }
    }

    private fun calculateRating(reviews: List<ReviewDTO>): Double {
        return if (reviews.isNotEmpty()) {
            reviews.sumOf { it.mark } / reviews.size.toDouble()
        } else {
            0.toDouble()
        }
    }
}

data class RouteReviewsInfo(
    val curUserReview: ReviewDTO?,
    val routes: List<ReviewDTO>,
    val rating: Double,
    val reviewsCount: Int
)
