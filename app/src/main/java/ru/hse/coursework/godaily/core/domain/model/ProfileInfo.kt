package ru.hse.coursework.godaily.core.domain.model

import ru.hse.coursework.godaily.core.data.model.RouteDTO

data class ProfileInfo(
    val id: String,
    val name: String,
    val photoUrl: String,
    val completedRoutes: List<RouteDTO> = emptyList(),
    val favouriteRoutes: List<RouteDTO> = emptyList()
)