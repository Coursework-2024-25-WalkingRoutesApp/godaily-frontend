package ru.hse.coursework.godaily.screen.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.screen.map.CreateRouteViewModel
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.NoRoutesBox
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun DraftsScreen(
    navController: NavController,
    createViewModel: CreateRouteViewModel,
    viewModel: RoutesViewModel = hiltViewModel(),
) {
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

            HeaderBig(text = "Черновики")
        }

        if (viewModel.drafts.isEmpty()) {
            NoRoutesBox()
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            RouteVerticalGrid(
                routes = viewModel.drafts,
                onRouteClick = { route ->
                    createViewModel.clear()
                    navController.navigate(NavigationItem.RouteCreationOnMap.route + "/${route.id}")
                }
            )
        }
    }
}