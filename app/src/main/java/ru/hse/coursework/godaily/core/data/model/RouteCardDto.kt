package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RouteCardDto(
    @JsonProperty("id") var id: UUID,
    @JsonProperty("routeName") var routeName: String?,
    @JsonProperty("duration") var duration: Double?,
    @JsonProperty("length") var length: Double?,
    @JsonProperty("routePreview") var routePreview: String?,
    @JsonProperty("distanceToUser") var distanceToUser: Double?,
    @JsonProperty("categories") var categories: List<RouteDto.Category>?
)