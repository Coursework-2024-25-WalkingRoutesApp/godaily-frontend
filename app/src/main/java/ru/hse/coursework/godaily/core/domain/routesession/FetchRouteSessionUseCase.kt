package ru.hse.coursework.godaily.core.domain.routesession

import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.service.UuidService
import java.util.UUID
import javax.inject.Inject

class FetchRouteSessionUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller,
    private val uuidService: UuidService
) {
    suspend fun execute(routeId: UUID): ApiCallResult<Any> {
        //TODO хардкод
        val routePageDTOResponse =
            safeApiCaller.safeApiCall {
                api.getRouteDetails(
                    routeId = routeId
                )
            }

        if (routePageDTOResponse !is ApiCallResult.Success) {
            return routePageDTOResponse
        }
        val routeSessionResponse =
            safeApiCaller.safeApiCall {
                api.getRouteSession(
                    routeId = routeId
                )
            }
        if (routeSessionResponse !is ApiCallResult.Success) {
            return routeSessionResponse
        }

        val routeCoordinates =
            routePageDTOResponse.data.routeCoordinate?.mapNotNull { coordinate ->
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

        val userCheckpoints = routeSessionResponse.data.userCheckpoint.mapNotNull { checkpoint ->
            routePageDTOResponse.data.routeCoordinate?.find { it.id == checkpoint.coordinateId }
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

        return ApiCallResult.Success(
            RoutePointsSession(
                id = routeSessionResponse.data.id,
                isFinished = routeSessionResponse.data.isFinished ?: false,
                routePoints = routeCoordinates,
                passedRoutePoints = userCheckpoints
            )
        )
    }
}

data class TitledPoint(
    val id: UUID?,
    val point: Point,
    var title: String,
    var description: String,
)

data class RoutePointsSession(
    val id: UUID?,
    val isFinished: Boolean,
    val routePoints: List<TitledPoint>,
    val passedRoutePoints: List<TitledPoint>
)
