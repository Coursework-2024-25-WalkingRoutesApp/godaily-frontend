package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RoutePageDto(
    @JsonProperty("id")
    var id: UUID?,

    @JsonProperty("routeName")
    var routeName: String?,

    @JsonProperty("description")
    var description: String?,

    @JsonProperty("duration")
    var duration: Double?,

    @JsonProperty("length")
    var length: Double?,

    @JsonProperty("startPoint")
    var startPoint: String?,

    @JsonProperty("endPoint")
    var endPoint: String?,

    @JsonProperty("routePreview")
    var routePreview: String?,

    @JsonProperty("isFavourite")
    var isFavourite: Boolean,

    @JsonProperty("routeCoordinate")
    var routeCoordinate: List<RouteDto.RouteCoordinate>?,

    @JsonProperty("categories")
    var categories: List<RouteDto.Category>?
)

