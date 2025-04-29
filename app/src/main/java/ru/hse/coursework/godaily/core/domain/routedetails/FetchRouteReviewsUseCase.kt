package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import java.util.UUID
import javax.inject.Inject

class FetchRouteReviewsUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(routeId: UUID): ApiCallResult<Any> {

        val reviewsInfoResponse = fetchReviewsInfo(routeId)

        if (reviewsInfoResponse !is ApiCallResult.Success) {
            return reviewsInfoResponse
        }

        val reviews = reviewsInfoResponse.data.reviews

        val curUserReview = findCurrentUserReview(reviews, reviewsInfoResponse.data.curUserId)
        val sortedReviews = sortReviews(reviews, curUserReview)
        val rating = calculateRating(reviews)

        return ApiCallResult.Success(
            RouteReviewsInfo(
                curUserReview = curUserReview,
                reviews = sortedReviews,
                rating = rating,
                reviewsCount = reviews.size
            )
        )
    }

    private suspend fun fetchReviewsInfo(routeId: UUID) = safeApiCaller.safeApiCall {
        api.getReviews(
            routeId = routeId,
        )
    }

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
