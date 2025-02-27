package ru.hse.coursework.godaily.ui.components.superorganisms

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
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
import ru.hse.coursework.godaily.core.domain.routedetails.TitledPoint
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.RouteInfo
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun YandexRouteDisplayView(
    modifier: Modifier = Modifier,
    routePoints: List<TitledPoint>,
    routeInfoClick: () -> Unit,
    backClick: () -> Unit,
) {
    val context = LocalContext.current
    val startIcon = ImageProvider.fromResource(context, R.drawable.start)
    val midIcon = ImageProvider.fromResource(context, R.drawable.point)
    val endIcon = ImageProvider.fromResource(context, R.drawable.finish)

    val mapView = remember { MapView(context) }

    LaunchedEffect(Unit) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    DisposableEffect(Unit) {
        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                mapView.apply {
                    val map = mapWindow.map

                    updateRoute(map, routePoints, startIcon, midIcon, endIcon)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        RouteInfo(
            onClick = routeInfoClick,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
        )
        Back(
            onClick = backClick,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        )
    }
}

private fun updateRoute(
    map: Map,
    routePoints: List<TitledPoint>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider
) {
    if (routePoints.size > 1) {
        val pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter()
        val requestPoints =
            routePoints.map { RequestPoint(it.point, RequestPointType.WAYPOINT, null, null, null) }

        pedestrianRouter.requestRoutes(
            requestPoints,
            TimeOptions(),
            RouteOptions(FitnessOptions(false, false)),
            object : Session.RouteListener {
                override fun onMasstransitRoutes(pedestrianRoutes: MutableList<Route>) {
                    pedestrianRoutes.firstOrNull()?.let { route ->
                        map.mapObjects.clear()

                        setPlacemarks(map, routePoints, startIcon, midIcon, endIcon)

                        map.mapObjects.addPolyline(Polyline(route.geometry.points)).apply {
                            setStrokeColor(purpleRoutes.toArgb())
                        }

                        val bounds = getRouteBounds(routePoints)
                        map.move(
                            CameraPosition(
                                bounds.center,
                                12.0f,
                                0.0f,
                                0.0f
                            ),
                            Animation(Animation.Type.SMOOTH, 1.5f),
                            null
                        )
                    }
                }

                override fun onMasstransitRoutesError(error: Error) {
                    Log.e("YandexMap", "Ошибка в получении маршрута: $error")
                }
            }
        )
    }
}

private fun getRouteBounds(routePoints: List<TitledPoint>): BoundingBox {
    var minLat = Double.MAX_VALUE
    var minLon = Double.MAX_VALUE
    var maxLat = Double.MIN_VALUE
    var maxLon = Double.MIN_VALUE

    routePoints.forEach { point ->
        val lat = point.point.latitude
        val lon = point.point.longitude

        if (lat < minLat) minLat = lat
        if (lat > maxLat) maxLat = lat
        if (lon < minLon) minLon = lon
        if (lon > maxLon) maxLon = lon
    }

    return BoundingBox(minLat, minLon, maxLat, maxLon)
}

data class BoundingBox(
    val minLat: Double,
    val minLon: Double,
    val maxLat: Double,
    val maxLon: Double
) {
    val center: com.yandex.mapkit.geometry.Point
        get() = com.yandex.mapkit.geometry.Point(
            (minLat + maxLat) / 2,
            (minLon + maxLon) / 2
        )

    val latitudeDelta: Double
        get() = maxLat - minLat

    val longitudeDelta: Double
        get() = maxLon - minLon
}


private fun setPlacemarks(
    map: Map,
    points: List<TitledPoint>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider
) {
    points.forEachIndexed { index, point ->
        val icon = when (index) {
            0 -> startIcon
            points.lastIndex -> endIcon
            else -> midIcon
        }

        map.mapObjects.addPlacemark().apply {
            geometry = point.point
            setIcon(icon)
            setIconStyle(IconStyle().apply { scale = 0.7f })
        }
    }


}