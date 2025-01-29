package ru.hse.coursework.godaily.ui.components.organisms

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
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
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun YandexMapCreateRouteView(
    modifier: Modifier = Modifier,
    startCameraPosition: CameraPosition = CameraPosition(
        Point(55.751244, 37.618423),
        14.0f,
        0.0f,
        0.0f
    ),
    routePoints: MutableList<Point>
) {
    val context = LocalContext.current

    val startIcon = ImageProvider.fromResource(context, R.drawable.end_point)
    val midIcon = ImageProvider.fromResource(context, R.drawable.mid_point)
    val endIcon = ImageProvider.fromResource(context, R.drawable.end_point)

    val inputListener = remember(context) {
        object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                routePoints.add(point)
                updateRoute(map, routePoints, startIcon, midIcon, endIcon)
            }

            override fun onMapLongTap(map: Map, point: Point) {}
        }
    }

    AndroidView(
        factory = { _ ->
            MapView(context).apply {
                val map = mapWindow.map

                map.move(startCameraPosition)
                map.addInputListener(inputListener)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

private fun updateRoute(
    map: Map,
    routePoints: List<Point>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider
) {

    if (routePoints.size < 2) {
        setPlacemarks(map, routePoints, startIcon, midIcon, endIcon)
        return
    }

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

                    setPlacemarks(map, routePoints, startIcon, midIcon, endIcon)

                    val routeGeometry = route.geometry
                    val polyline = map.mapObjects.addPolyline(routeGeometry)
                    polyline.setStrokeColor(purpleRoutes.toArgb())

                }
            }

            override fun onMasstransitRoutesError(error: Error) {
                Log.e("YandexMap", "Error in pedestrian routing")
            }
        }
    )
}

private fun setPlacemarks(
    map: Map,
    points: List<Point>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider,
) {
    points.forEachIndexed { index, point ->
        map.mapObjects.addPlacemark().apply {
            geometry = point
            setIcon(
                when (index) {
                    0 -> startIcon
                    points.lastIndex -> endIcon
                    else -> midIcon
                }
            )
        }
    }

}