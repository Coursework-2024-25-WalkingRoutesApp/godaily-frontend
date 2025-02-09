package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.util.UUID
import javax.inject.Inject

class FetchRouteDetailsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: UUID): RouteDetails {
        val routePageDTO = api.getRouteDetails("", routeId)
        val reviews = api.getReviews("", routeId).reviews

        val averageMark = if (reviews.isNotEmpty()) {
            reviews.sumOf { it.rating } / reviews.size.toDouble()
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
    val route: RoutePageDto,
    val mark: Double,
    val reviewsCount: Int
)