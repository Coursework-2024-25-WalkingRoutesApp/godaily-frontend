package ru.hse.coursework.godaily.screen.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.CreateButton
import ru.hse.coursework.godaily.ui.components.organisms.Drafts
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun RoutesScreen(
    navController: NavController,
    viewModel: RoutesViewModel = hiltViewModel()
) {
    val publishedRoutes = viewModel.publishedRoutes
    val drafts = viewModel.drafts

    viewModel.loadRoutesScreenInfo()

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
                //TODO: в отдельную функцию
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
                if (drafts.isNotEmpty()) {
                    Drafts(
                        routeCount = drafts.size,
                        modifier = Modifier.padding(start = 16.dp, bottom = 30.dp)
                    )
                }
                RouteVerticalGrid(
                    routes = publishedRoutes,
                    onRouteClick = { route ->
                        navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                    }
                )
            }
        }

        CreateButton(
            onClick = { navController.navigate(NavigationItem.RouteCreationOnMap.route) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
        )
    }
}
