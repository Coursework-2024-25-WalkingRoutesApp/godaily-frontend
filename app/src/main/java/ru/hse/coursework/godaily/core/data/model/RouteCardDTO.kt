package ru.hse.coursework.godaily.core.data.model

data class RouteCardDTO(
    val id: String,
    val routeName: String,
    val duration: Int,
    val length: Long,
    val routePreview: String,
    val categories: List<Category>
)