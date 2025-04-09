package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.service.SortRouteService
import javax.inject.Inject

class SortRoutesUseCase @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
) {
    fun execute(
        routes: MutableList<RouteCardDto>,
        sortOption: Int,
    ): MutableList<RouteCardDto> {
        return SortRouteService(fetchRouteDetailsUseCase).sortRoutes(routes, sortOption)
    }
}

