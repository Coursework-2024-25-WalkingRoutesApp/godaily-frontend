package ru.hse.coursework.godaily.core.domain.routesession

import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.service.UuidService
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SaveRouteSessionUseCase @Inject constructor(
    private val api: ApiService,
    private val uuidService: UuidService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        id: UUID?,
        routeId: UUID,
        passedPoints: List<TitledPoint>,
        routePoints: List<TitledPoint>
    ): ApiCallResult<String> {
        val isFinished = (passedPoints.size == routePoints.size)

        return safeApiCaller.safeApiCall {
            api.saveRouteSession(
                routeSessionDto = RouteSessionDto(
                    id = id,
                    routeId = routeId,
                    isFinished = isFinished,
                    startedAt = LocalDateTime.now(),
                    endedAt = LocalDateTime.now(),
                    userCheckpoint = createUserCoordinateFromPoints(passedPoints),
                )
            )
        }
    }

    private fun createUserCoordinateFromPoints(passedPoints: List<TitledPoint>): List<RouteSessionDto.UserCheckpoint> {
        return passedPoints.map { titledPoint ->
            RouteSessionDto.UserCheckpoint(
                coordinateId = titledPoint.id,
                createdAt = LocalDateTime.now()
            )
        }
    }


}
