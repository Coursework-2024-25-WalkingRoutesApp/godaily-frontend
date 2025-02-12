package ru.hse.coursework.godaily.ui.components.superorganisms

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.FitnessOptions
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.RouteOptions
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.organisms.RouteNavigationBox
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun YandexMapNavigationView(
    modifier: Modifier = Modifier,
    routePoints: List<Point>,
    passedPoints: SnapshotStateList<Point>
) {
    val context = LocalContext.current
    val currentPointIndex = remember { mutableStateOf(0) }
    val startIcon = ImageProvider.fromResource(context, R.drawable.start)
    val midIcon = ImageProvider.fromResource(context, R.drawable.point)
    val endIcon = ImageProvider.fromResource(context, R.drawable.finish)
    val passedIconStart = ImageProvider.fromResource(context, R.drawable.passed_start)
    val passedIconPoint = ImageProvider.fromResource(context, R.drawable.passed_point)
    val passedIconFinish = ImageProvider.fromResource(context, R.drawable.passed_finish)

    val mapView = remember { MapView(context) }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 160.dp),
            update = { view ->
                val map = view.mapWindow.map
                if (routePoints.isNotEmpty()) {
                    map.move(CameraPosition(routePoints[0], 14.0f, 0.0f, 0.0f))
                }
                updateRoute(
                    map, routePoints, passedPoints,
                    startIcon, midIcon, endIcon,
                    passedIconStart, passedIconPoint, passedIconFinish
                )
            }
        )

        RouteNavigationBox(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onNextPointClick = {
                if (currentPointIndex.value < routePoints.size) {
                    val newPoint = routePoints[currentPointIndex.value]
                    passedPoints.add(newPoint)
                    currentPointIndex.value++

                    mapView.mapWindow.map.move(
                        CameraPosition(newPoint, 14.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 1.5f),
                        null
                    )

                    updateRoute(
                        mapView.mapWindow.map, routePoints, passedPoints,
                        startIcon, midIcon, endIcon,
                        passedIconStart, passedIconPoint, passedIconFinish
                    )
                }
            },
            onPauseClick = { /*TODO*/ }
        )
    }
}


private fun updateRoute(
    map: Map,
    routePoints: List<Point>,
    passedPoints: SnapshotStateList<Point>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider,
    passedIconStart: ImageProvider,
    passedIconPoint: ImageProvider,
    passedIconFinish: ImageProvider
) {
    if (routePoints.size > 1) {
        val pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter()
        val requestPoints =
            routePoints.map { RequestPoint(it, RequestPointType.WAYPOINT, null, null, null) }

        pedestrianRouter.requestRoutes(
            requestPoints,
            TimeOptions(),
            RouteOptions(FitnessOptions(false, false)),
            object : Session.RouteListener {
                override fun onMasstransitRoutes(pedestrianRoutes: MutableList<Route>) {
                    pedestrianRoutes.firstOrNull()?.let { route ->

                        map.mapObjects.clear()

                        setPlacemarks(
                            map = map,
                            points = routePoints,
                            startIcon = startIcon,
                            midIcon = midIcon,
                            endIcon = endIcon,
                            passedIconStart = passedIconStart,
                            passedIconPoint = passedIconPoint,
                            passedIconFinish = passedIconFinish,
                            passedPoints = passedPoints
                        )

                        updatePassedRouteSegments(map, route.geometry, passedPoints)


                    }
                }

                override fun onMasstransitRoutesError(error: Error) {
                    Log.e("YandexMap", "Ошибка в получении маршрута: $error")
                }
            }
        )
    }
}

private fun updatePassedRouteSegments(
    map: Map,
    routeGeometry: Polyline,
    passedPoints: List<Point>
) {
    if (passedPoints.isEmpty()) {
        map.mapObjects.addPolyline(routeGeometry).apply {
            setStrokeColor(purpleRoutes.toArgb())
        }
        return
    }

    val routePoints = routeGeometry.points
    val lastPassedPoint = passedPoints.last()

    val routePassedPoints =
        routePoints.takeWhile { it.latitude != lastPassedPoint.latitude && it.longitude != lastPassedPoint.longitude } + lastPassedPoint
    val routeNotPassedPoints = routePoints.drop(routePassedPoints.size)

    if (routeNotPassedPoints.isNotEmpty()) {
        map.mapObjects.addPolyline(Polyline(routeNotPassedPoints)).apply {
            setStrokeColor(purpleRoutes.toArgb())
        }
    }


    if (routePassedPoints.size > 1) {
        map.mapObjects.addPolyline(Polyline(routePassedPoints)).apply {
            setStrokeColor(greyDark.toArgb())
        }
    }
}


private fun setPlacemarks(
    map: Map,
    points: List<Point>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider,
    passedIconStart: ImageProvider,
    passedIconPoint: ImageProvider,
    passedIconFinish: ImageProvider,
    passedPoints: SnapshotStateList<Point>
) {
    points.forEachIndexed { index, point ->
        val isPassed =
            passedPoints.any { it.latitude == point.latitude && it.longitude == point.longitude }
        val icon = when {
            index == 0 && isPassed -> passedIconStart
            index == 0 -> startIcon
            index == points.lastIndex && isPassed -> passedIconFinish
            index == points.lastIndex -> endIcon
            isPassed -> passedIconPoint
            else -> midIcon
        }

        map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(icon)
            setIconStyle(IconStyle().apply { scale = 0.7f })
        }
    }
}
