package ru.hse.coursework.godaily.ui.components.superorganisms

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.ui.components.organisms.AddPointTitleDialog
import ru.hse.coursework.godaily.ui.theme.purpleRoutes
import java.util.UUID

@Composable
fun YandexMapCreateRouteView(
    modifier: Modifier = Modifier,
    startCameraPosition: CameraPosition = CameraPosition(
        Point(55.751244, 37.618423),
        14.0f,
        0.0f,
        0.0f
    ),
    showAddPointTitleDialog: MutableState<Boolean>,
    routePoints: MutableList<TitledPoint>
) {
    val context = LocalContext.current

    val startIcon = ImageProvider.fromResource(context, R.drawable.start)
    val midIcon = ImageProvider.fromResource(context, R.drawable.point)
    val endIcon = ImageProvider.fromResource(context, R.drawable.finish)

    val selectedPoint = remember { mutableStateOf<TitledPoint?>(null) }
    val isAddingPoint = remember { mutableStateOf(false) }


    val mapView = remember { MapView(context) }
    val coroutineScope = rememberCoroutineScope()

    val inputListener = remember(context) {
        object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                if (isAddingPoint.value) return

                isAddingPoint.value = true

                val newPoint = TitledPoint(UUID.randomUUID(), point, "", "")
                routePoints.add(newPoint)
                selectedPoint.value = newPoint
                updateRoute(map, routePoints, startIcon, midIcon, endIcon)
                coroutineScope.launch {
                    delay(800)
                    showAddPointTitleDialog.value = true
                    isAddingPoint.value = false
                }
            }

            override fun onMapLongTap(map: Map, point: Point) {}
        }
    }

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

    AndroidView(
        factory = { _ ->
            mapView.apply {
                val map = mapWindow.map

                if (routePoints.isNotEmpty()) {
                    map.move(CameraPosition(routePoints.last().point, 14.0f, 0.0f, 0.0f))
                }
                map.move(startCameraPosition)


                map.addInputListener(inputListener)
                updateRoute(map, routePoints, startIcon, midIcon, endIcon)
            }
        },
        modifier = modifier.fillMaxSize(),
        update = { view ->
            val map = view.mapWindow.map
            if (routePoints.isNotEmpty()) {
                map.move(
                    CameraPosition(routePoints.last().point, 18.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1.5f),
                    null
                )
            }

            updateRoute(map, routePoints, startIcon, midIcon, endIcon)
        }
    )

    selectedPoint.value?.let { point ->
        if (showAddPointTitleDialog.value) {
            AddPointTitleDialog(
                showDialog = showAddPointTitleDialog,
                title = remember { mutableStateOf(point.title) },
                description = remember { mutableStateOf(point.description) },
                onSaveClick = { title, description ->
                    point.title = title
                    point.description = description
                    showAddPointTitleDialog.value = false
                },
                onKeepWithNoTitleClick = {
                    showAddPointTitleDialog.value = false
                }
            )

        }
    }
}


private fun updateRoute(
    map: Map,
    routePoints: MutableList<TitledPoint>,
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
    points: MutableList<TitledPoint>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider,
) {
    points.forEachIndexed { index, point ->
        map.mapObjects.addPlacemark().apply {
            geometry = point.point
            setIcon(
                when (index) {
                    0 -> startIcon
                    points.lastIndex -> endIcon
                    else -> midIcon
                }
            )
            setIconStyle(IconStyle().apply { scale = 0.7f })
        }
    }

}