package ru.hse.coursework.godaily.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem
import ru.hse.coursework.godaily.ui.theme.greyDark


//TODO: обработать нажатие на маршруты
@Composable
fun CompletedRoutesScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

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

        if (state.completedRoutes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                VariableMedium(
                    text = "Пока нет таких маршрутов",
                    fontSize = 16.sp,
                    fontColor = greyDark
                )
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            RouteVerticalGrid(
                routes = state.completedRoutes,
                onRouteClick = { route ->
                    navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                }
            )
        }
    }
}

