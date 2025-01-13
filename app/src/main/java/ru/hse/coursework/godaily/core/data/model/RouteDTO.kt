package ru.hse.coursework.godaily.core.data.model

data class RouteDTO(
    val id: String,
    val userId: String,
    val routeName: String,
    val description: String,
    val duration: Int,
    val length: Long,
    val startPoint: String,
    val endPoint: String,
    val routePreview: String,
    val isDraft: Boolean,
    val lastModifiedAt: String,
    val createdAt: String
)

