package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.RouteRating
import ru.hse.coursework.godaily.ui.components.superorganisms.ReviewGrid

@Composable
fun RouteReviewsScreen(
    navController: NavController,
    routeId: String,
    viewModel: RouteReviewsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    viewModel.loadRouteReviews(routeId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Back(
            onClick = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        HeaderBig(text = "Отзывы")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            RouteRating(
                rating = state.rating,
                reviewsCount = state.reviewsCount,
                onRatingSelected = {/*TODO*/}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ReviewGrid(reviews = state.reviews)
    }
}

