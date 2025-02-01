package ru.hse.coursework.godaily.core.data.model

import java.time.LocalDateTime
import java.util.UUID

data class ReviewDto(
    var curUserId: UUID? = null,
    var reviews: List<ReviewInfoDto>,
) {
    data class ReviewInfoDto(
        var userId: UUID,
        var userName: String,
        var userPhotoUrl: String?,
        var reviewText: String?,
        var rating: Int,
        val createdAt: LocalDateTime
    )
}
