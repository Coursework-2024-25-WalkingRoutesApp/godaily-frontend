package ru.hse.coursework.godaily.core.domain.routedetails

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.time.LocalDateTime
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
            //TODO хардкод
            userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            routeId = routeId,
            mark = rating,
            reviewText = reviewText ?: "",
            createdAt = createdAt
        )
    }
}