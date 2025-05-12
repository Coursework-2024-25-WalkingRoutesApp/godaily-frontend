package ru.hse.coursework.godaily.core.domain.routes

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import coil3.ImageLoader
import com.yandex.mapkit.geometry.Point
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
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
    private val safeApiCaller: SafeApiCaller,
    private val imageLoader: ImageLoader
) {
    suspend fun execute(
        id: UUID?,
        routeName: String,
        description: String,
        startPoint: String,
        endPoint: String,
        imageUri: Uri?,
        photoUrl: String?,
        isDraft: Boolean,
        routePoints: SnapshotStateList<TitledPoint>,
        categories: Set<Int>,
    ): ApiCallResult<String> {
        val route = routeYandexService.createRoute(titledPointsToPoints(routePoints))
        var routePreview: String? = null

        val duration = route?.let { routeYandexService.getRouteDuration(it) } ?: 0.toDouble()
        val length = route?.let { routeYandexService.getRouteLength(it) } ?: 0.toDouble()

        val routePreviewMultipart = imageUri?.let {
            photoConverterService.uriToMultipart(it)
        }
        routePreviewMultipart?.let { multipart ->
            val typePart = "route".toRequestBody("text/plain".toMediaType())
            val photoUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            val savePhotoResult = safeApiCaller.safeApiCall {
                api.uploadPhoto(multipart, typePart, photoUrlPart)
            }

            if (savePhotoResult is ApiCallResult.Success) {
                routePreview = savePhotoResult.data
            }
        }

        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        return safeApiCaller.safeApiCall {
            api.addRoute(
                routeDto = RouteDto(
                    id = id,
                    routeName = routeName,
                    description = description,
                    duration = duration,
                    length = length,
                    startPoint = startPoint,
                    endPoint = endPoint,
                    routePreview = routePreview,
                    isDraft = isDraft,
                    routeCoordinate = titledPointsToRouteCoordinate(routePoints, id),
                    categories = categoriesToDto(id, categories)
                ),
            )
        }
    }

    private fun titledPointsToPoints(routePoints: SnapshotStateList<TitledPoint>): List<Point> {
        return routePoints.map { it.point }
    }

    private fun titledPointsToRouteCoordinate(
        routePoints: SnapshotStateList<TitledPoint>,
        routeId: UUID?
    ): List<RouteDto.RouteCoordinate> {
        return routePoints.mapIndexed { index, titledPoint ->
            RouteDto.RouteCoordinate(
                id = null,
                routeId = routeId,
                latitude = titledPoint.point.latitude,
                longitude = titledPoint.point.longitude,
                orderNumber = index + 1,
                title = titledPoint.title,
                description = titledPoint.description
            )
        }
    }

    private fun categoriesToDto(routeId: UUID?, categories: Set<Int>): List<RouteDto.Category> {
        val categoryMap = mapOf(
            0 to "Природный",
            1 to "Культурно-исторический",
            2 to "Кафе по пути",
            3 to "У метро"
        )
        return categories.mapNotNull { categoryId ->
            categoryMap[categoryId]?.let { categoryName ->
                RouteDto.Category(routeId, categoryName)
            }
        }
    }

}
