package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RouteCoordinate(
    @JsonProperty("latitude")
    val latitude: Double,

    @JsonProperty("longitude")
    val longitude: Double,

    @JsonProperty("order")
    val order: Int
)