package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SaveReviewUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        routeId: UUID,
        reviewText: String?,
        rating: Int,
        createdAt: LocalDateTime = LocalDateTime.now()
    ): ApiCallResult<String> {
        return safeApiCaller.safeApiCall {
            api.saveReview(
                routeId = routeId,
                mark = rating,
                reviewText = reviewText ?: "",
                createdAt = createdAt
            )
        }
    }
}