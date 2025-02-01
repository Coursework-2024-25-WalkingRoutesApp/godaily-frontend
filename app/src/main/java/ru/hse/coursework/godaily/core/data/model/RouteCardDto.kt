package ru.hse.coursework.godaily.core.data.model

import java.time.LocalTime
import java.util.UUID

data class RouteCardDto(
    var id: UUID,
    var routeName: String?,
    var duration: LocalTime?,
    var length: Long?,
    var routePreview: String?,
    var distanceToUser: Double,
    var categories: List<RouteDto.Category>?
)