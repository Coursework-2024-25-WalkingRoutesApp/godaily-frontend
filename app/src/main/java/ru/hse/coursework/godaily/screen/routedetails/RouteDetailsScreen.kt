package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val state by viewModel.uiState.collectAsState()

    viewModel.loadRouteDetails(routeId)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RouteDetailsCard(
            route = state.route,
            mark = state.mark,
            reviewsCount = state.reviewsCount,
            onBackClick = { navController.popBackStack() },
            onMapClick = { /*TODO*/ },
            onFavouriteToggle = { /*TODO*/ },
            onReviewsClick = { navController.navigate(NavigationItem.RouteReviews.route + "/${routeId}") }
        )

        Spacer(modifier = Modifier.weight(1f))

        StartButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

