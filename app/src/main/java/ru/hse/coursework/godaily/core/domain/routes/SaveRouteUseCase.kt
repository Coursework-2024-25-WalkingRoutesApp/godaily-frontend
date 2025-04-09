package ru.hse.coursework.godaily.core.domain.routes

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.core.domain.service.PhotoConverterService
import ru.hse.coursework.godaily.core.domain.service.RouteYandexService
import java.util.UUID
import javax.inject.Inject

class SaveRouteUseCase @Inject constructor(
    private val api: ApiService,
    private val routeYandexService: RouteYandexService,
    private val photoConverterService: PhotoConverterService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        id: UUID?,
        routeName: String,
        description: String,
        startPoint: String,
        endPoint: String,
        imageUri: Uri?,
        isDraft: Boolean,
        routePoints: SnapshotStateList<TitledPoint>,
        categories: Set<Int>,
    ): ApiCallResult<String> {
        val route = routeYandexService.createRoute(titledPointsToPoints(routePoints))

        val duration = route?.let { routeYandexService.getRouteDuration(it) } ?: 0.toDouble()
        val length = route?.let { routeYandexService.getRouteLength(it) } ?: 0.toDouble()
        val routeId = id ?: UUID.randomUUID()

        val routePreview = imageUri?.let {
            photoConverterService.uriToByteArray(it)
        }

        return safeApiCaller.safeApiCall {
            api.addRoute(
                //TODO хардкод
                userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                routeDto = RouteDto(
                    id = routeId,
                    routeName = routeName,
                    description = description,
                    duration = duration,
                    length = length,
                    startPoint = startPoint,
                    endPoint = endPoint,
                    routePreview = routePreview,
                    isDraft = isDraft,
                    routeCoordinate = titledPointsToRouteCoordinate(routePoints, routeId),
                    categories = categoriesToDto(categories)
                )
            )
        }
    }

    private fun titledPointsToPoints(routePoints: SnapshotStateList<TitledPoint>): List<Point> {
        return routePoints.map { it.point }
    }

    private fun titledPointsToRouteCoordinate(
        routePoints: SnapshotStateList<TitledPoint>,
        routeId: UUID
    ): List<RouteDto.RouteCoordinate> {
        return routePoints.mapIndexed { index, titledPoint ->
            RouteDto.RouteCoordinate(
                id = titledPoint.id,
                routeId = routeId,
                latitude = titledPoint.point.latitude,
                longitude = titledPoint.point.longitude,
                orderNumber = index + 1,
                title = titledPoint.title,
                description = titledPoint.description
            )
        }
    }

    private fun categoriesToDto(categories: Set<Int>): List<String> {
        val categoryMap = mapOf(
            0 to "Природный",
            1 to "Культурно-исторический",
            2 to "Кафе по пути",
            3 to "У метро"
        )
        return categories.mapNotNull { categoryMap[it] }
    }

}
