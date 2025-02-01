package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchRoutesForHomeScreenUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(userCoordinate: String): List<RouteCardDto> {
        return api.getRoutesSortedByDistance("", userCoordinate)
    }
}