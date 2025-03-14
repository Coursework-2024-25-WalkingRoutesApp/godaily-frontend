package ru.hse.coursework.godaily.core.domain.routedetails

import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
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

        val routeCoordinates = routePageDTO.routeCoordinate?.mapNotNull { coordinate ->
            coordinate.latitude?.let { lat ->
                coordinate.longitude?.let { lon ->
                    TitledPoint(
                        coordinate.id,
                        Point(lat, lon),
                        coordinate.title ?: "",
                        coordinate.description ?: ""
                    )
                }
            }
        } ?: emptyList()



        return RouteDetails(
            route = routePageDTO,
            mark = averageMark,
            reviewsCount = reviews.size,
            routePoints = routeCoordinates,
        )

    }
}

data class RouteDetails(
    val route: RoutePageDto,
    val mark: Double,
    val reviewsCount: Int,
    val routePoints: List<TitledPoint>
)