package ru.hse.coursework.godaily.core.domain

import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchRouteDetailsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: String): RouteDetails {
        val routePageDTO = api.getRouteDetails("", routeId)
        val reviews = api.getReviews("", routeId)

        val averageMark = if (reviews.isNotEmpty()) {
            reviews.sumOf { it.mark } / reviews.size.toDouble()
        } else {
            0.toDouble()
        }

        return RouteDetails(
            route = routePageDTO,
            mark = averageMark,
            reviewsCount = reviews.size
        )

    }
}

data class RouteDetails(
    val route: RoutePageDTO,
    val mark: Double,
    val reviewsCount: Int
)