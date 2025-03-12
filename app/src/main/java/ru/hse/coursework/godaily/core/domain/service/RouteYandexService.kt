package ru.hse.coursework.godaily.core.domain.service

import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.FitnessOptions
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.RouteOptions
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.runtime.Error
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject
import kotlin.coroutines.resume

class RouteYandexService @Inject constructor(
    private val locationService: LocationService
) {
    suspend fun createRoute(points: List<Point>): Route? =
        suspendCancellableCoroutine { cont ->
            val pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter()
            val requestPoints =
                points.map { RequestPoint(it, RequestPointType.WAYPOINT, null, null, null) }

            val session = pedestrianRouter.requestRoutes(
                requestPoints,
                TimeOptions(),
                RouteOptions(FitnessOptions(false, false)),
                object : Session.RouteListener {
                    override fun onMasstransitRoutes(pedestrianRoutes: MutableList<Route>) {
                        cont.resume(pedestrianRoutes.firstOrNull())
                    }

                    override fun onMasstransitRoutesError(error: Error) {
                        cont.resume(null)
                    }
                }
            )

            cont.invokeOnCancellation { session.cancel() }
        }

    // Длина маршрута в метрах
    fun getRouteLength(route: Route): Double {
        return route.metadata.weight.walkingDistance.value
    }

    // Длина маршрута текстом
    fun getRouteLengthText(route: Route): String {
        return route.metadata.weight.walkingDistance.text
    }

    // Продолжительность маршрута в секундах
    fun getRouteDuration(route: Route): Double {
        return route.metadata.weight.time.value
    }

    suspend fun getDistancePointToPoint(firstPoint: Point, secondPoint: Point): Double? {
        return createRoute(listOf(firstPoint, secondPoint))?.let { getRouteLength(it) }
    }

    // Продолжительность маршрута текстом
    fun getRouteDurationText(route: Route): String {
        return route.metadata.weight.time.text
    }

    // Расстояние до текущего пользователя в метрах
    suspend fun getRouteStartDistanceFromUser(
        startRoutePoint: Point
    ): Double? {
        val curUserPoint = locationService.getCurrentLocation()

        return if (curUserPoint != null) {
            createRoute(listOf(curUserPoint, startRoutePoint))?.let { getRouteLength(it) }
        } else {
            null
        }
    }

    suspend fun getRouteStartDistanceFromUserText(
        startRoutePoint: Point
    ): String? {
        val curUserPoint = locationService.getCurrentLocation()

        return if (curUserPoint != null) {
            createRoute(listOf(curUserPoint, startRoutePoint))?.let { getRouteLengthText(it) }
        } else {
            null
        }
    }

}