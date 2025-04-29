package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class ReviewDto(
    @JsonProperty("curUserId")
    var curUserId: UUID? = null,

    @JsonProperty("reviews")
    var reviews: List<ReviewInfoDto>
) {
    data class ReviewInfoDto(
        @JsonProperty("userId")
        var userId: UUID,

        @JsonProperty("userName")
        var userName: String,

        @JsonProperty("photoUrl")
        var userPhotoUrl: String?,

        @JsonProperty("reviewText")
        var reviewText: String?,

        @JsonProperty("rating")
        var rating: Int,

        @JsonProperty("createdAt")
        val createdAt: LocalDateTime
    )
}
