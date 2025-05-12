package ru.hse.coursework.godaily.ui.components.superorganisms

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SuggestItem
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestResponse
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.FitnessOptions
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.RouteOptions
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.ui.components.molecules.Cancel
import ru.hse.coursework.godaily.ui.components.organisms.AddPointTitleDialog
import ru.hse.coursework.godaily.ui.components.organisms.SearchBarWithSuggestions
import ru.hse.coursework.godaily.ui.notification.ToastManager
import ru.hse.coursework.godaily.ui.theme.purpleRoutes
import java.util.UUID
import com.yandex.mapkit.search.Session as SearchSession
import com.yandex.mapkit.transport.masstransit.Session as RouteSession

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

    val searchQuery = remember { mutableStateOf("") }
    val suggestItems = remember { SnapshotStateList<SuggestItem>() }

    val mapView = remember { MapView(context) }
    val coroutineScope = rememberCoroutineScope()

    val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
    val suggestSession = searchManager.createSuggestSession()
    val suggestOptions = SuggestOptions()

    val suggestListener = object : SuggestSession.SuggestListener {
        override fun onResponse(p0: SuggestResponse) {
            suggestItems.clear()
            suggestItems.addAll(p0.items.take(5))
        }

        override fun onError(error: Error) {}
    }

    val inputListener = remember(context) {
        object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                if (isAddingPoint.value) return

                coroutineScope.launch {
                    isAddingPoint.value = true

                    val newPoint = TitledPoint(UUID.randomUUID(), point, "", "")
                    routePoints.add(newPoint)
                    selectedPoint.value = newPoint
                    updateRoute(map, routePoints, startIcon, midIcon, endIcon)

                    delay(300)
                    showAddPointTitleDialog.value = true
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

    Box(modifier = modifier.fillMaxSize()) {
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
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                val map = view.mapWindow.map
                if (routePoints.isNotEmpty()) {
                    map.move(
                        CameraPosition(routePoints.last().point, 17.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 1.5f),
                        null
                    )
                }

                updateRoute(map, routePoints, startIcon, midIcon, endIcon)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SearchBarWithSuggestions(
                query = searchQuery,
                suggestions = suggestItems,
                onValueChange = {
                    suggestSession.suggest(
                        searchQuery.value,
                        BoundingBox(
                            mapView.mapWindow.map.visibleRegion.bottomLeft,
                            mapView.mapWindow.map.visibleRegion.topRight
                        ),
                        suggestOptions,
                        suggestListener,
                    )
                },
                onSearchClick = {
                    searchManager.submit(
                        searchQuery.value,
                        VisibleRegionUtils.toPolygon(mapView.mapWindow.map.visibleRegion),
                        SearchOptions(),
                        object : SearchSession.SearchListener {
                            override fun onSearchResponse(response: Response) {
                                val searchResults = response.collection.children
                                if (searchResults.isNotEmpty()) {
                                    val point = searchResults[0].obj?.geometry?.get(0)?.point
                                    if (point != null) {
                                        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                                            geometry = point
                                            setIcon(
                                                ImageProvider.fromResource(
                                                    context,
                                                    R.drawable.search_placemark
                                                )
                                            )
                                            setIconStyle(IconStyle().apply { scale = 0.5f })
                                        }
                                        mapView.mapWindow.map.move(
                                            CameraPosition(point, 16.0f, 0.0f, 0.0f),
                                            Animation(Animation.Type.SMOOTH, 1f),
                                            null
                                        )
                                    }
                                }
                            }

                            override fun onSearchError(error: Error) {
                                Log.e("SearchError", "Произошла ошибка при поиске: $error")
                            }
                        }
                    )
                },
                onSuggestionClick = { suggestion ->
                    suggestion.center?.let { point ->
                        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                            geometry = point
                            setIcon(
                                ImageProvider.fromResource(
                                    context,
                                    R.drawable.search_placemark
                                )
                            )
                            setIconStyle(IconStyle().apply { scale = 0.5f })
                        }

                        mapView.mapWindow.map.move(
                            CameraPosition(
                                point,
                                14f,
                                0f,
                                0f
                            ),
                            Animation(Animation.Type.SMOOTH, 1f),
                            null
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.Top)
                    .width(330.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            if (routePoints.isNotEmpty()) {
                Cancel(
                    onClick = {
                        routePoints.removeLastOrNull()
                        mapView.mapWindow.map.mapObjects.clear()
                        updateRoute(mapView.mapWindow.map, routePoints, startIcon, midIcon, endIcon)
                    },
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(top = 16.dp, end = 16.dp)
                        .size(40.dp)
                        .widthIn(min = 40.dp)
                )
            }
        }
    }

    selectedPoint.value?.let { point ->
        if (showAddPointTitleDialog.value) {
            AddPointTitleDialog(
                showDialog = showAddPointTitleDialog,
                title = remember { mutableStateOf(point.title) },
                description = remember { mutableStateOf(point.description) },
                onSaveClick = { title, description ->
                    if (title == "") {
                        ToastManager(context).showToast("У точки должно быть название")
                        showAddPointTitleDialog.value = true
                    } else {
                        point.title = title
                        point.description = description
                        isAddingPoint.value = false
                    }
                },
                onKeepWithNoTitleClick = {
                    isAddingPoint.value = false
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
        object : RouteSession.RouteListener {
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
            setIconStyle(IconStyle().apply { scale = 0.2f })
        }
    }
}