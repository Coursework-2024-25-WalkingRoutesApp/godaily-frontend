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
        //TODO хардкод
        val routePageDTO = api.getRouteDetails(
            routeId = routeId,
            userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412")
        )
        val reviews = api.getReviews(
            routeId = routeId,
            userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412")
        ).reviews

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