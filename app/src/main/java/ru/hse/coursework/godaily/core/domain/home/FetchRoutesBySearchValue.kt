package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FetchRoutesBySearchValue @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService,
    private val safeApiCaller: SafeApiCaller,
    private val searchRadiusMeters: Long
) {
    suspend fun execute(
        searchValue: String
    ): ApiCallResult<List<RouteCardDto>> {
        val userCoordinates = locationService.getUserCoordinate()
        return safeApiCaller.safeApiCall {
            api.getRoutesBySearchValue(
                searchValue,
                userCoordinates.latitude,
                userCoordinates.longitude,
                searchRadiusMeters
            )
        }
    }
}
