package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FetchUnfinishedRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(): List<RouteCardDto> {
        return api.getUserUnfinishedRoutes("", locationService.getUserCoordinate())
    }
}