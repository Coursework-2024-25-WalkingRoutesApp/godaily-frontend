package ru.hse.coursework.godaily.core.domain.model

import ru.hse.coursework.godaily.core.data.model.RouteCardDto

//TODO нужно ли?
data class ProfileInfo(
    val email: String,
    val username: String,
    val photoUrl: String?,
    val completedRoutes: List<RouteCardDto> = emptyList(),
    val favouriteRoutes: List<RouteCardDto> = emptyList()
)