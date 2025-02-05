package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.util.UUID
import javax.inject.Inject

class FetchRouteReviewsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: String): RouteReviewsInfo {

        val reviewsInfo = fetchReviewsInfo(routeId)
        val reviews = reviewsInfo.reviews

        val curUserReview = findCurrentUserReview(reviews, reviewsInfo.curUserId)
        val sortedReviews = sortReviews(reviews, curUserReview)
        val rating = calculateRating(reviews)

        return RouteReviewsInfo(
            curUserReview = curUserReview,
            reviews = sortedReviews,
            rating = rating,
            reviewsCount = reviews.size
        )
    }

    private suspend fun fetchReviewsInfo(routeId: String) = api.getReviews("", routeId)

    private fun findCurrentUserReview(
        reviews: List<ReviewDto.ReviewInfoDto>,
        curUserId: UUID?
    ): ReviewDto.ReviewInfoDto? {
        return reviews.find { it.userId == curUserId }
    }

    private fun sortReviews(
        reviews: List<ReviewDto.ReviewInfoDto>,
        curUserReview: ReviewDto.ReviewInfoDto?
    ): List<ReviewDto.ReviewInfoDto> {
        return if (curUserReview != null) {
            listOf(curUserReview) + reviews.filter { it != curUserReview }
                .sortedByDescending { it.createdAt }
        } else {
            reviews.sortedByDescending { it.createdAt }
        }
    }

    private fun calculateRating(reviews: List<ReviewDto.ReviewInfoDto>): Double {
        return if (reviews.isNotEmpty()) {
            reviews.sumOf { it.rating } / reviews.size.toDouble()
        } else {
            0.toDouble()
        }
    }
}

data class RouteReviewsInfo(
    val curUserReview: ReviewDto.ReviewInfoDto?,
    val reviews: List<ReviewDto.ReviewInfoDto>,
    val rating: Double,
    val reviewsCount: Int
)
