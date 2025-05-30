package ru.hse.coursework.godaily.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.NoRoutesBox
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun CompletedRoutesScreen(
    navController: NavController,
    routeDetailsViewModel: RouteDetailsViewModel,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val isLoading = viewModel.isLoading
    LaunchedEffect(navController) {
        viewModel.loadUserData()
    }

    LoadingScreenWrapper(isLoading = isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Back(
                    onClick = { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                HeaderBig(text = "Пройденные маршруты")
            }

            if (viewModel.completedRoutes.isEmpty()) {
                NoRoutesBox()
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                RouteVerticalGrid(
                    routes = viewModel.completedRoutes,
                    onRouteClick = { route ->
                        viewModel.trackRouteDetailsOpen(route.id, route.routeName)
                        routeDetailsViewModel.clear()
                        navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                    },
                    imageLoader = viewModel.imageLoader
                )
            }
        }
    }
}

