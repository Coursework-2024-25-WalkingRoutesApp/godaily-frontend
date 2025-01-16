package ru.hse.coursework.godaily.core.data.model

import java.time.LocalDateTime

data class ReviewDTO(
    val username: String,
    val photoURL: String,
    val mark: Int,
    val reviewText: String,
    val createdAt: LocalDateTime
)
