package ru.hse.coursework.godaily.core.data.model

import java.util.UUID

data class RouteCardDto(
    var id: UUID,
    var routeName: String?,
    var duration: Double?,
    var length: Double?,
    var routePreview: String?,
    var distanceToUser: Double?,
    var categories: List<RouteDto.Category>?
)