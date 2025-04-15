package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.ApplyButton
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.superorganisms.RateRouteCard

@Composable
fun RateRouteScreen(
    navController: NavController,
    routeId: String,
    mark: Int,
    viewModel: RouteDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val routeState = viewModel.route
    val markState = viewModel.userMark
    val reviewTextState = viewModel.reviewText

    LaunchedEffect(routeId, mark) {
        viewModel.loadRateReviewDetails(routeId, mark)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Back(
                onClick = { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(16.dp))

            HeaderBig(text = "Ваш отзыв")
        }

        Spacer(modifier = Modifier.height(16.dp))

        RateRouteCard(
            title = routeState.value.routeName ?: "Название",
            startPoint = routeState.value.startPoint ?: "Стартовая точка",
            endPoint = routeState.value.endPoint ?: "Конечная точка",
            imageUrl = routeState.value.routePreview ?: "",
            mark = markState,
            reviewText = reviewTextState
        )

        Spacer(modifier = Modifier.weight(1f))

        ApplyButton(
            onClick = {
                viewModel.saveReview(context)
                navController.popBackStack()
            },
            text = "Сохранить",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

