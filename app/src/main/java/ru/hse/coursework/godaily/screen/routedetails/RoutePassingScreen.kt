package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yandex.mapkit.MapKitFactory
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.superorganisms.YandexMapNavigationView

@Composable
fun RoutePassingScreen(
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        MapKitFactory.getInstance().onStart()

        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(routeId) {
        viewModel.loadRouteDetails(routeId)
        viewModel.loadSessionPoints(routeId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        YandexMapNavigationView(
            routeTitle = viewModel.route.value.routeName ?: "Маршрут",
            routePoints = viewModel.routePoints,
            passedPoints = viewModel.passedPoints
        )
        Back(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}