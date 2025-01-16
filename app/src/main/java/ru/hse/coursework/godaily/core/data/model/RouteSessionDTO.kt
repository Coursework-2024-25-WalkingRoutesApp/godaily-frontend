package ru.hse.coursework.godaily.core.data.model

data class RouteSessionDTO(
    val id: String,
    val routeId: String,
    val isFinished: Boolean,
    val startedAt: String,
    val endedAt: String?,
    val userCheckpoints: List<UserCheckpointDTO>
)