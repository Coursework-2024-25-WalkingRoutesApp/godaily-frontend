package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.components.organisms.RouteRatingForDetailsCard
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteDetailsCard
import ru.hse.coursework.godaily.ui.components.superorganisms.YandexRouteDisplayView
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun RouteDetailsScreen(
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel(),
) {
    val isLoading = viewModel.isLoading
    val routeState = viewModel.route
    val markState = viewModel.averageMark
    val reviewsCount = viewModel.reviewsCount
    val isFavourite = viewModel.isFavourite

    val showMap = remember { mutableStateOf(false) }

    LaunchedEffect(routeId) {
        viewModel.loadRouteDetails(routeId)
        viewModel.loadSessionPoints(routeId)
        viewModel.loadRouteReviews(routeId)
    }

    LoadingScreenWrapper(isLoading = isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!showMap.value) {
                RouteDetailsCard(
                    route = routeState.value,
                    isFavourite = isFavourite,
                    onBackClick = { navController.popBackStack() },
                    onMapClick = { showMap.value = true },
                    onFavouriteToggle = { boolValue ->
                        if (!boolValue) {
                            viewModel.addRouteToFavourites()
                        } else {
                            viewModel.removeRouteFromFavourites()
                        }
                        viewModel.updateIsFavourite()
                    },
                    modifier = Modifier.weight(1f)
                )

                RouteRatingForDetailsCard(
                    rating = markState.value,
                    reviewsCount = reviewsCount.value,
                    onReviewsClick = { navController.navigate(NavigationItem.RouteReviews.route + "/${routeId}") },
                )

                StartButton(
                    onClick = { navController.navigate(NavigationItem.RoutePassing.route + "/${routeId}") },
                    modifier = Modifier
                        .padding(20.dp)
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    YandexRouteDisplayView(
                        routePoints = viewModel.routePoints,
                        modifier = Modifier.fillMaxSize(),
                        routeInfoClick = { showMap.value = false },
                        backClick = { navController.popBackStack() }
                    )

                    StartButton(
                        onClick = { navController.navigate(NavigationItem.RoutePassing.route + "/${routeId}") },
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
