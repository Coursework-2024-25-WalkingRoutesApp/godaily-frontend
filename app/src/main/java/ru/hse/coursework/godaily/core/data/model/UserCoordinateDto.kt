package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCoordinateDto(
    @JsonProperty("latitude") var latitude: Double,
    @JsonProperty("longitude") var longitude: Double
)