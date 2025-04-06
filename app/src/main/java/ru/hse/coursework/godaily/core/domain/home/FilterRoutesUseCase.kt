package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

class FilterRoutesUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(
        filterCategories: Set<Int>,
    ): List<RouteCardDto> {
        val userCoordinates = locationService.getUserCoordinate()
        val categoriesString = convertCategories(filterCategories)
        return api.filterRoutesByCategoryAndDistance(
            //TODO хардкод
            UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            userCoordinates.latitude,
            userCoordinates.longitude,
            categoriesString
        )
    }

    private fun convertCategories(categories: Set<Int>): List<String> {
        val indexToCategoryName = mapOf(
//            0 to "Nature",
//            1 to "Culture",
//            2 to "Coffee",
//            3 to "Metro"
            0 to "Природный",
            1 to "Культурно-исторический",
            2 to "Кафе по пути",
            3 to "У метро"
        )

        return categories.mapNotNull { indexToCategoryName[it] }
    }
}
