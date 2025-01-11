package ru.hse.coursework.godaily.model

import java.time.LocalTime

class Route(
    val userId: String = "",
    val routeName: String = "",
    val description: String = "",
    val duration: LocalTime = LocalTime.MIN,
    val length: Long = 0,
    val startPoint: String = "",
    val endPoint: String = "",
    val routePreview: Int = 0,
    val isDraft: Boolean = false,
)