package ru.hse.coursework.godaily.core.domain.routes

import com.yandex.mapkit.geometry.Point
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.service.RouteYandexService
import java.util.UUID
import javax.inject.Inject

class SaveRouteUseCase @Inject constructor(
    private val api: ApiService,
    private val routeYandexService: RouteYandexService
) {
    suspend fun execute(
        id: UUID?,
        routeName: String,
        description: String,
        startPoint: String,
        endPoint: String,
        //TODO: bytearray
        routePreview: String,
        isDraft: Boolean,
        points: List<Point>,
        routeCoordinate: List<RouteDto.RouteCoordinate>,
        categories: List<RouteDto.Category>
    ): Response<String> {
        val route = routeYandexService.createRoute(points)

        val duration = route?.let { routeYandexService.getRouteDuration(it) } ?: 0.toDouble()
        val length = route?.let { routeYandexService.getRouteLength(it) } ?: 0.toDouble()

        return api.addRoute(
            "",
            RouteDto(
                id = id ?: UUID.randomUUID(),
                routeName = routeName,
                description = description,
                duration = duration,
                length = length,
                startPoint = startPoint,
                endPoint = endPoint,
                routePreview = routePreview, // TODO
                isDraft = isDraft,
                routeCoordinate = routeCoordinate, // TODO
                categories = categories // TODO
            )
        )
    }
}
