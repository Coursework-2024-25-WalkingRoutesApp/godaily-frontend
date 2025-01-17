package ru.hse.coursework.godaily.core.domain.model

import ru.hse.coursework.godaily.core.data.model.RouteCardDTO

data class ProfileInfo(
    val email: String,
    val username: String,
    val photoUrl: String,
    val completedRoutes: List<RouteCardDTO> = emptyList(),
    val favouriteRoutes: List<RouteCardDTO> = emptyList()
)