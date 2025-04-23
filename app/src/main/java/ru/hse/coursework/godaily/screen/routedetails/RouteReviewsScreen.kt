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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.RouteRatingWithChoice
import ru.hse.coursework.godaily.ui.components.organisms.RouteRatingWithoutChoice
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.components.superorganisms.ReviewGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun RouteReviewsScreen(
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel(),
) {
    val isLoading = viewModel.isLoading
    val curUserReview = viewModel.curUserReview
    val reviews = viewModel.reviews
    val averageMark = viewModel.averageMark
    val reviewsCount = viewModel.reviewsCount

    LaunchedEffect(navController) {
        viewModel.loadRouteReviews(routeId)
    }


    LoadingScreenWrapper(isLoading = isLoading) {
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
                if (curUserReview.value != null) {
                    RouteRatingWithoutChoice(
                        rating = averageMark.value,
                        reviewsCount = reviewsCount.value,
                    )
                } else {
                    RouteRatingWithChoice(
                        rating = averageMark.value,
                        reviewsCount = reviewsCount.value,
                        onRatingSelected = { mark ->
                            navController.navigate(NavigationItem.RouteRate.route + "/${routeId}/${mark}")
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            ReviewGrid(reviews = reviews)
        }
    }
}

