package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class RouteSessionDto(
    @JsonProperty("id")
    var id: UUID?,

    @JsonProperty("routeId")
    var routeId: UUID,

    @JsonProperty("isFinished")
    var isFinished: Boolean?,

    @JsonProperty("startedAt")
    var startedAt: LocalDateTime?,

    @JsonProperty("endedAt")
    var endedAt: LocalDateTime?,

    @JsonProperty("userCheckpoint")
    var userCheckpoint: List<UserCheckpoint>
) {
    data class UserCheckpoint(
        @JsonProperty("coordinateId")
        var coordinateId: UUID?,

        @JsonProperty("createdAt")
        var createdAt: LocalDateTime?
    )
}