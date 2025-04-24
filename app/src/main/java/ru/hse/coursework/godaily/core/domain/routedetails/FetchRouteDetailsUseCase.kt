package ru.hse.coursework.godaily.core.domain.routedetails

import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import java.util.UUID
import javax.inject.Inject

class FetchRouteDetailsUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(routeId: UUID): ApiCallResult<Any> {
        val routePageDTOResponse = safeApiCaller.safeApiCall {
            api.getRouteDetails(
                routeId = routeId,
            )
        }
        if (routePageDTOResponse !is ApiCallResult.Success) {
            return routePageDTOResponse
        }

        val reviewsResponse = safeApiCaller.safeApiCall {
            api.getReviews(
                routeId = routeId,
            )
        }
        if (reviewsResponse !is ApiCallResult.Success) {
            return reviewsResponse
        }

        val reviews = reviewsResponse.data.reviews

        val averageMark = if (reviews.isNotEmpty()) {
            reviews.sumOf { it.rating } / reviews.size.toDouble()
        } else {
            0.toDouble()
        }

        val routeCoordinates = routePageDTOResponse.data.routeCoordinate?.mapNotNull { coordinate ->
            coordinate.latitude?.let { lat ->
                coordinate.longitude?.let { lon ->
                    TitledPoint(
                        coordinate.id,
                        Point(lat, lon),
                        coordinate.title ?: "",
                        coordinate.description ?: ""
                    )
                }
            }
        } ?: emptyList()

        return ApiCallResult.Success(
            RouteDetails(
                route = routePageDTOResponse.data,
                mark = averageMark,
                reviewsCount = reviewsResponse.data.reviews.size,
                routePoints = routeCoordinates
            )
        )
    }
}

data class RouteDetails(
    val route: RoutePageDto,
    val mark: Double,
    val reviewsCount: Int,
    val routePoints: List<TitledPoint>
)