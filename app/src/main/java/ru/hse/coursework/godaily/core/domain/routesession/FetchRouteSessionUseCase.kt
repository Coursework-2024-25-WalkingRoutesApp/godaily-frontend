package ru.hse.coursework.godaily.core.domain.routesession

import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.util.UUID
import javax.inject.Inject

class FetchRouteSessionUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(routeId: UUID): RoutePointsSession {
        //TODO хардкод
        val routePageDTO =
            api.getRouteDetails(
                userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                routeId = routeId
            )
        val routeSession = api.getRouteSession("", routeId)

        val routeCoordinates = routePageDTO.routeCoordinate?.mapNotNull { coordinate ->
            coordinate.latitude?.let { lat ->
                coordinate.longitude?.let { lon ->
                    TitledPoint(
                        id = coordinate.id,
                        point = Point(lat, lon),
                        title = coordinate.title ?: "",
                        description = coordinate.description ?: ""
                    )
                }
            }
        } ?: emptyList()

        val userCheckpoints = routeSession.userCheckpoint.mapNotNull { checkpoint ->
            routePageDTO.routeCoordinate?.find { it.id == checkpoint.coordinateId }
                ?.let { coordinate ->
                    coordinate.latitude?.let { lat ->
                        coordinate.longitude?.let { lon ->
                            TitledPoint(
                                id = coordinate.id,
                                point = Point(lat, lon),
                                title = coordinate.title ?: "",
                                description = coordinate.description ?: ""
                            )
                        }
                    }
                }
        }

        return RoutePointsSession(
            id = routeSession.id,
            isFinished = routeSession.isFinished ?: false,
            routePoints = routeCoordinates,
            passedRoutePoints = userCheckpoints
        )
    }
}

data class TitledPoint(
    val id: UUID,
    val point: Point,
    var title: String,
    var description: String,
)

data class RoutePointsSession(
    val id: UUID,
    val isFinished: Boolean,
    val routePoints: List<TitledPoint>,
    val passedRoutePoints: List<TitledPoint>
)
