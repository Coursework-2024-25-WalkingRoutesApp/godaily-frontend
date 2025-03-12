package ru.hse.coursework.godaily.core.data.model

import java.util.UUID

data class RouteDto(
    var id: UUID,
    var routeName: String?,
    var description: String?,
    var duration: Double?,
    var length: Double?,
    var startPoint: String?,
    var endPoint: String?,
    var routePreview: String?,
    var isDraft: Boolean?,
    var routeCoordinate: List<RouteCoordinate>?,
    var categories: List<Category>?
) {

    data class RouteCoordinate(
        var id: UUID,
        var routeId: UUID,
        var latitude: Double?,
        var longitude: Double?,
        var orderNumber: Int?,
        val title: String?,
        val description: String?
    )

    data class Category(
        var routeId: UUID,
        var categoryName: String
    )
}

