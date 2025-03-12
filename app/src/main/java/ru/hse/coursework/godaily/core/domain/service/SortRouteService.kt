package ru.hse.coursework.godaily.core.domain.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase

class SortRouteService(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase
) {
    fun sortRoutes(
        routes: MutableList<RouteCardDto>,
        selectedSortOption: Int
    ): MutableList<RouteCardDto> {
        return when (selectedSortOption) {
            0 -> routes.sortedBy { it.distanceToUser ?: Double.MAX_VALUE }
            1 -> runBlocking(Dispatchers.IO) {
                routes.map { route ->
                    val routeDetails = fetchRouteDetailsUseCase.execute(route.id)
                    route to routeDetails.mark
                }.sortedByDescending { it.second }
                    .map { it.first }
            }

            2 -> routes.sortedByDescending { it.length ?: 0.0 }
            3 -> routes.sortedBy { it.length ?: Double.MAX_VALUE }
            else -> routes
        }.toMutableList()
    }
}

