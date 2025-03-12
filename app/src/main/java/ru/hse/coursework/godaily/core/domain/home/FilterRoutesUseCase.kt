package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FilterRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(
        filterCategories: Set<Int>,
    ): List<RouteCardDto> {
        return api.filterRoutesByCategoryAndDistance(
            "",
            locationService.getUserCoordinate(),
            filterCategories
        )
    }
}
