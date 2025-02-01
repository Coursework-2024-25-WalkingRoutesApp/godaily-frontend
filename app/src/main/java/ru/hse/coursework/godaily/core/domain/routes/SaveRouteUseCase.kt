package ru.hse.coursework.godaily.core.domain.routes

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class SaveRouteUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        id: UUID?,
        routeName: String,
        description: String,
        duration: LocalTime,
        length: Long,
        startPoint: String,
        endPoint: String,
        //TODO: bytearray
        routePreview: String,
        isDraft: Boolean,
        routeCoordinate: List<RouteDto.RouteCoordinate>,
        categories: List<RouteDto.Category>
    ): Response<String> {
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
                //TODO: bytearray
                routePreview = routePreview,
                isDraft = isDraft,
                routeCoordinate = routeCoordinate,
                categories = categories
            )
        )
    }
}