package ru.hse.coursework.godaily.core.data.model

import java.time.LocalDateTime

//TODO: айди нужен?
data class UserCheckpointDTO(
    val latitude: Double,
    val longitude: Double,
    val timestamp: LocalDateTime
)
