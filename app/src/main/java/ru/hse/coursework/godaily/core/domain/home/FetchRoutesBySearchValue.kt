package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FetchRoutesBySearchValue @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(
        searchValue: String
    ): List<RouteCardDto> {
        return api.getRoutesBySearchValue("", locationService.getUserCoordinate(), searchValue)
    }
}
