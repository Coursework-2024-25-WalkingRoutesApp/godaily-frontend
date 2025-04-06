package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class ReviewPublishDto(
    @JsonProperty("routeId")
    val routeId: UUID,

    @JsonProperty("reviewText")
    var reviewText: String?,

    @JsonProperty("rating")
    var rating: Int,

    @JsonProperty("createdAt")
    val createdAt: LocalDateTime
)