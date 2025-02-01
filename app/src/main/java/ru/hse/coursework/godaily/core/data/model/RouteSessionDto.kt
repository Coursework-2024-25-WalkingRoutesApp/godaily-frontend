package ru.hse.coursework.godaily.core.data.model

import java.time.LocalDateTime
import java.util.UUID

data class RouteSessionDto(
    var id: UUID,
    var routeId: UUID,
    var isFinished: Boolean?,
    var startedAt: LocalDateTime?,
    var endedAt: LocalDateTime?,
    var userCheckpoint: List<UserCheckpoint>
) {

    data class UserCheckpoint(
        var coordinateId: UUID,
        var createdAt: LocalDateTime?
    )
}