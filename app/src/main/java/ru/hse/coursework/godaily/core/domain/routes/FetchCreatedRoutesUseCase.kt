package ru.hse.coursework.godaily.core.domain.routes

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

class FetchCreatedRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(): List<RouteCardDto> {
        val userLocation = locationService.getUserCoordinate()
        return api.getUserPublishedRoutes(
            UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            userLocation.latitude,
            userLocation.longitude
        )
    }
}