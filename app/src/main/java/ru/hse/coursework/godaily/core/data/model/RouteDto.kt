package ru.hse.coursework.godaily.core.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RouteDto(
    @JsonProperty("id") var id: UUID,
    @JsonProperty("routeName") var routeName: String? = null,
    @JsonProperty("description") var description: String? = null,
    @JsonProperty("duration") var duration: Double? = null,
    @JsonProperty("length") var length: Double? = null,
    @JsonProperty("startPoint") var startPoint: String? = null,
    @JsonProperty("endPoint") var endPoint: String? = null,
    @JsonProperty("routePreview") var routePreview: ByteArray? = null,
    @JsonProperty("isDraft") var isDraft: Boolean? = null,
    @JsonProperty("routeCoordinate") var routeCoordinate: List<RouteCoordinate>? = null,
    @JsonProperty("categories") var categories: List<String>? = null
) {

    data class RouteCoordinate(
        @JsonProperty("id") var id: UUID,
        @JsonProperty("routeId") var routeId: UUID,
        @JsonProperty("latitude") var latitude: Double? = null,
        @JsonProperty("longitude") var longitude: Double? = null,
        @JsonProperty("orderNumber") var orderNumber: Int? = null,
        @JsonProperty("title") val title: String? = null,
        @JsonProperty("description") val description: String? = null
    )

    data class Category(
        @JsonProperty("routeId") var routeId: UUID,
        @JsonProperty("categoryName") var categoryName: String
    )
}