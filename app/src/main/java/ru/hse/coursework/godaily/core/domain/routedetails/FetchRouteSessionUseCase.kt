package ru.hse.coursework.godaily.core.domain.routedetails

import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.util.UUID
import javax.inject.Inject

class FetchRouteSessionUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: UUID): RoutePointsSession {
        val routePageDTO = api.getRouteDetails("", routeId)
        val routeSession = api.getRouteSession("", routeId)

        val routeCoordinates = routePageDTO.routeCoordinate?.mapNotNull { coordinate ->
            coordinate.latitude?.let { lat ->
                coordinate.longitude?.let { lon ->
                    Point(lat, lon)
                }
            }
        } ?: emptyList()

        val userCheckpoints = routeSession.userCheckpoint.mapNotNull { checkpoint ->
            routePageDTO.routeCoordinate?.find { it.id == checkpoint.coordinateId }
                ?.let { coordinate ->
                    coordinate.latitude?.let { lat ->
                        coordinate.longitude?.let { lon ->
                            Point(lat, lon)
                        }
                    }
                }
        }

        return RoutePointsSession(
            isFinished = routeSession.isFinished ?: false,
            routePoints = routeCoordinates,
            passedRoutePoints = userCheckpoints
        )
    }
}

data class RoutePointsSession(
    val isFinished: Boolean,
    val routePoints: List<Point>,
    val passedRoutePoints: List<Point>
)
