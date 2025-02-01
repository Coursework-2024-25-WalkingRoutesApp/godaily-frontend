package ru.hse.coursework.godaily.core.data.model

import java.time.LocalDateTime
import java.util.UUID

data class ReviewPublishDto (
    val routeId: UUID,
    var reviewText: String?,
    var rating: Int,
    val createdAt: LocalDateTime
)