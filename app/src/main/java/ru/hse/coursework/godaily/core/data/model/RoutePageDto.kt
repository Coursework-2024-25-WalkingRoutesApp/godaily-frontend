package ru.hse.coursework.godaily.core.data.model

import java.util.UUID

data class RoutePageDto(
    var id: UUID,
    var routeName: String?,
    var description: String?,
    var duration: Double?,
    var length: Double?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isFavourite: Boolean,
    var routeCoordinate: List<RouteDto.RouteCoordinate>?,
    var categories: List<RouteDto.Category>?
)

