package ru.hse.coursework.godaily.screen.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.screen.map.CreateRouteViewModel
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.CreateButton
import ru.hse.coursework.godaily.ui.components.organisms.Drafts
import ru.hse.coursework.godaily.ui.components.organisms.NoRoutesBox
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun RoutesScreen(
    navController: NavController,
    createViewModel: CreateRouteViewModel,
    routeDetailsViewModel: RouteDetailsViewModel,
    viewModel: RoutesViewModel = hiltViewModel()
) {
    val publishedRoutes = viewModel.publishedRoutes
    val drafts = viewModel.drafts

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 65.dp)
        ) {
            HeaderBig(
                text = "Мои маршруты",
                modifier = Modifier.padding(top = 20.dp, start = 16.dp, bottom = 30.dp)
            )

            if (publishedRoutes.isEmpty() && drafts.isEmpty()) {
                NoRoutesBox()
            } else {
                if (drafts.isNotEmpty()) {
                    Drafts(
                        routeCount = drafts.size,
                        modifier = Modifier.padding(start = 16.dp, bottom = 30.dp),
                        onClick = { navController.navigate(NavigationItem.Drafts.route) }
                    )
                }
                RouteVerticalGrid(
                    routes = publishedRoutes,
                    onRouteClick = { route ->
                        viewModel.trackRouteDetailsOpen(route.id, route.routeName)
                        routeDetailsViewModel.clear()
                        navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                    }
                )
            }
        }

        CreateButton(
            onClick = {
                createViewModel.clear()
                navController.navigate(NavigationItem.RouteCreationOnMap.route + "/${""}")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
        )
    }
}
