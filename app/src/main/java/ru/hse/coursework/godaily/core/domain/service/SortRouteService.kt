package ru.hse.coursework.godaily.core.domain.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RouteDetails

class SortRouteService(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase
) {
    fun sortRoutes(
        routes: MutableList<RouteCardDto>,
        selectedSortOption: Int
    ): MutableList<RouteCardDto> {
        val sortedRoutes: MutableList<RouteCardDto> = when (selectedSortOption) {
            0 -> routes.sortedBy { it.distanceToUser ?: Double.MAX_VALUE }
            1 -> runBlocking(Dispatchers.IO) {
                routes
                    .map { route ->
                        val result = fetchRouteDetailsUseCase.execute(route.id)
                        when (result) {
                            is ApiCallResult.Success -> {
                                if (result.data is RouteDetails) {
                                    val mark = result.data.mark
                                    route to mark
                                } else {
                                    route to 0.0
                                }
                            }

                            is ApiCallResult.Error -> {
                                route to 0.0
                            }
                        }
                    }
                    .sortedByDescending { (_, mark) -> mark }
                    .map { (route, _) -> route }
            }

            2 -> routes.sortedByDescending { it.length ?: 0.0 }
            3 -> routes.sortedBy { it.length ?: Double.MAX_VALUE }
            else -> routes
        }.toMutableList()

        return sortedRoutes
    }
}

