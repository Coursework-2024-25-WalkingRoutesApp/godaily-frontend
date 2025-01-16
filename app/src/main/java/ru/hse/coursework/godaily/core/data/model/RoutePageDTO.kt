package ru.hse.coursework.godaily.core.data.model

data class RoutePageDTO(
    val id: String,
    val routeName: String,
    val description: String,
    val duration: Int,
    val length: Long,
    val startPoint: String,
    val endPoint: String,
    val routePreview: String,
    val isFavourite: Boolean,
    val coordinates: List<RouteCoordinateDTO>,
    val categories: List<Category>
)

