package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteDetailsCard
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun RouteDetailsScreen(
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel(),
) {
    val routeState = viewModel.route
    val markState = viewModel.averageMark
    val reviewsCount = viewModel.reviewsCount
    val isFavourite = viewModel.isFavourite

    LaunchedEffect(routeId) {
        viewModel.loadRouteDetails(routeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RouteDetailsCard(
            route = routeState.value,
            mark = markState.value,
            isFavourite = isFavourite,
            reviewsCount = reviewsCount.value,
            onBackClick = { navController.popBackStack() },
            onMapClick = { /*TODO*/ },
            onFavouriteToggle = { boolValue ->
                if (!boolValue) {
                    viewModel.addRouteToFavourites()
                } else {
                    viewModel.removeRouteFromFavourites()
                }
                viewModel.updateIsFavourite()
            },
            onReviewsClick = { navController.navigate(NavigationItem.RouteReviews.route + "/${routeId}") }
        )

        Spacer(modifier = Modifier.weight(1f))

        StartButton(
            onClick = { navController.navigate(NavigationItem.RoutePassing.route + "/${routeId}") },
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

