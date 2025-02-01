package ru.hse.coursework.godaily.core.domain.routedetails

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.ReviewPublishDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class SaveReviewUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        routeId: UUID,
        reviewText: String?,
        rating: Int,
        createdAt: LocalDateTime = LocalDateTime.now()
    ): Response<String> {
        return api.saveReview(
            "",
            ReviewPublishDto(
                routeId,
                reviewText,
                rating,
                createdAt
            )
        )
    }
}