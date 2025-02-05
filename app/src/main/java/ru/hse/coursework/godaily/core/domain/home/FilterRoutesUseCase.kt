package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FilterRoutesUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        userCoordinate: String,
        sortCategories: Set<Int>,
    ): List<RouteCardDto> {
        return api.filterRoutesByCategoryAndDistance("", userCoordinate, sortCategories)
    }
}
