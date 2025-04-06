package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

class FetchRoutesForHomeScreenUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(): List<RouteCardDto> {
        //TODO исправить
        val userCoordinates = locationService.getUserCoordinate()
        return api.getRoutesForHomePage(
            //TODO хардкод
            UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            userCoordinates.latitude,
            userCoordinates.longitude
        )
    }
}