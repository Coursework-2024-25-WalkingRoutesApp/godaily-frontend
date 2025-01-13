package ru.hse.coursework.godaily.core.data.model

data class ReviewDTO(
    val userId: String,
    val routeId: String,
    val mark: Int,
    val reviewText: String
)
