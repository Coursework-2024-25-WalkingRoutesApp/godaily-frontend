package ru.hse.coursework.godaily.core.data.model

data class ReviewsInfoDTO(
    val userId: String,
    val reviews: List<ReviewDTO>
)