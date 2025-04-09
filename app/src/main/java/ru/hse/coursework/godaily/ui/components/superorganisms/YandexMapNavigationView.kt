package ru.hse.coursework.godaily.ui.components.superorganisms

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.layers.ObjectEvent
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
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.ui.components.organisms.RouteNavigationBox
import ru.hse.coursework.godaily.ui.components.organisms.RouteStartBox
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun YandexMapNavigationView(
    modifier: Modifier = Modifier,
    routeTitle: String,
    routePoints: List<TitledPoint>,
    passedPoints: SnapshotStateList<TitledPoint>,
    viewModel: RouteDetailsViewModel,
    onPauseClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    val context = LocalContext.current
    val currentPointIndex = mutableStateOf(passedPoints.size)
    val startIcon = ImageProvider.fromResource(context, R.drawable.start)
    val midIcon = ImageProvider.fromResource(context, R.drawable.point)
    val endIcon = ImageProvider.fromResource(context, R.drawable.finish)
    val passedIconStart = ImageProvider.fromResource(context, R.drawable.passed_start)
    val passedIconPoint = ImageProvider.fromResource(context, R.drawable.passed_point)
    val passedIconFinish = ImageProvider.fromResource(context, R.drawable.passed_finish)

    val distanceToNewPoint = viewModel.distanceToNextPoint

    val mapView = remember { MapView(context) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mapView.mapWindow.map.apply {
                val userLocationLayer =
                    MapKitFactory.getInstance().createUserLocationLayer(mapView.mapWindow).apply {
                        isVisible = true
                        isHeadingEnabled = true
                    }
                userLocationLayer.cameraPosition()?.let {
                    move(it)
                }
            }
        } else {
            Toast.makeText(context, "Геолокация не разрешена", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
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
                    val userLocationLayer =
                        MapKitFactory.getInstance().createUserLocationLayer(mapWindow).apply {
                            isVisible = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                            isHeadingEnabled = isVisible
                        }

                    userLocationLayer.setObjectListener(object : UserLocationObjectListener {
                        override fun onObjectAdded(userLocationView: UserLocationView) {
                            userLocationLayer.resetAnchor()
                            userLocationView.arrow.setIcon(
                                ImageProvider.fromResource(
                                    context,
                                    R.drawable.geolocation
                                )
                            )
                            userLocationView.pin.setIcon(
                                ImageProvider.fromResource(
                                    context,
                                    R.drawable.geolocation
                                )
                            )

                            userLocationView.accuracyCircle.fillColor =
                                ColorUtils.setAlphaComponent(
                                    greyLight.toArgb(), 153
                                )

                        }

                        override fun onObjectRemoved(p0: UserLocationView) {}
                        override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
                        }
                    })
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 160.dp),
            update = { view ->
                val map = view.mapWindow.map
                if (routePoints.isNotEmpty()) {
                    map.move(
                        CameraPosition(routePoints[0].point, 14.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 1.5f),
                        null
                    )
                }
                if (passedPoints.isNotEmpty()) {
                    map.move(
                        CameraPosition(passedPoints.last().point, 14.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 1.5f),
                        null
                    )
                }
                updateRoute(
                    map, routePoints, passedPoints,
                    startIcon, midIcon, endIcon,
                    passedIconStart, passedIconPoint, passedIconFinish
                )
            }
        )

        val nextPoint = if (currentPointIndex.value < routePoints.size) {
            routePoints[currentPointIndex.value]
        } else null

        if (currentPointIndex.value == 0) {
            RouteStartBox(
                onStartClick = {
                    if (routePoints.isNotEmpty()) {
                        passedPoints.add(routePoints.first())
                        currentPointIndex.value++
                    }
                },
                routeTitle = routeTitle,
                startPoint = routePoints.firstOrNull()?.title ?: "Начальная точка"
            )
        } else {
            if (nextPoint != null) {
                viewModel.updateDistanceToNextPoint(
                    routePoints[currentPointIndex.value - 1].point,
                    nextPoint.point
                )
            }
            RouteNavigationBox(
                distanceToNextPoint = distanceToNewPoint.value,
                nextPointTitle = nextPoint?.title?.takeIf { it.isNotEmpty() }
                    ?: "До следующей точки",
                nextPointSubtitle = nextPoint?.description ?: "",
                onNextPointText = if (nextPoint == null) "Завершить" else "Следующая точка",
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onNextPointClick = {
                    if (nextPoint != null) {
                        if (currentPointIndex.value < routePoints.size) {
                            val newPoint = routePoints[currentPointIndex.value]
                            val map = mapView.mapWindow.map
                            passedPoints.add(newPoint)

                            currentPointIndex.value++

                            map.move(
                                CameraPosition(newPoint.point, 10.0f, 0.0f, 0.0f),
                                Animation(Animation.Type.SMOOTH, 1.5f),
                                null
                            )

                            updateRoute(
                                mapView.mapWindow.map, routePoints, passedPoints,
                                startIcon, midIcon, endIcon,
                                passedIconStart, passedIconPoint, passedIconFinish
                            )
                        }
                    } else {
                        onFinishClick()
                    }
                },
                onPauseClick = onPauseClick
            )
        }
    }
}


private fun updateRoute(
    map: Map,
    routePoints: List<TitledPoint>,
    passedPoints: SnapshotStateList<TitledPoint>,
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
            routePoints.map { RequestPoint(it.point, RequestPointType.WAYPOINT, null, null, null) }

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
    passedPoints: List<TitledPoint>
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
        routePoints.takeWhile { it.latitude != lastPassedPoint.point.latitude && it.longitude != lastPassedPoint.point.longitude } + lastPassedPoint.point
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
    points: List<TitledPoint>,
    startIcon: ImageProvider,
    midIcon: ImageProvider,
    endIcon: ImageProvider,
    passedIconStart: ImageProvider,
    passedIconPoint: ImageProvider,
    passedIconFinish: ImageProvider,
    passedPoints: SnapshotStateList<TitledPoint>
) {
    points.forEachIndexed { index, point ->
        val isPassed =
            passedPoints.any { it.point.latitude == point.point.latitude && it.point.longitude == point.point.longitude }
        val icon = when {
            index == 0 && isPassed -> passedIconStart
            index == 0 -> startIcon
            index == points.lastIndex && isPassed -> passedIconFinish
            index == points.lastIndex -> endIcon
            isPassed -> passedIconPoint
            else -> midIcon
        }

        map.mapObjects.addPlacemark().apply {
            geometry = point.point
            setIcon(icon)
            setIconStyle(IconStyle().apply { scale = 0.7f })
        }
    }
}