package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

class FetchUnfinishedRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(): ApiCallResult<List<RouteCardDto>> {
        val userCoordinates = locationService.getUserCoordinate()
        return safeApiCaller.safeApiCall {
            api.getUserUnfinishedRoutes(//TODO "",
                //TODO хардкод
                UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                userCoordinates.latitude, userCoordinates.longitude
            )
        }
    }
}