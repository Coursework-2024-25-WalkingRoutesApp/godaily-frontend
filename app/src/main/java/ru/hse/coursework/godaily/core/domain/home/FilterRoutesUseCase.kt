package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FilterRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService,
    private val safeApiCaller: SafeApiCaller,
    private val searchRadiusMeters: Long
) {
    suspend fun execute(
        filterCategories: Set<Int>,
    ): ApiCallResult<List<RouteCardDto>> {
        val userCoordinates = locationService.getUserCoordinate()
        val categoriesString = convertCategories(filterCategories)
        return safeApiCaller.safeApiCall {
            api.filterRoutesByCategoryAndDistance(
                userCoordinates.latitude,
                userCoordinates.longitude,
                categoriesString,
                searchRadiusMeters
            )
        }
    }

    private fun convertCategories(categories: Set<Int>): List<String> {
        val indexToCategoryName = mapOf(
            0 to "Природный",
            1 to "Культурно-исторический",
            2 to "Кафе по пути",
            3 to "У метро"
        )

        return categories.mapNotNull { indexToCategoryName[it] }
    }
}
