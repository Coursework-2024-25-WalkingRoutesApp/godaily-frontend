package ru.hse.coursework.godaily.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yandex.mapkit.MapKitFactory
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.ApplyButton
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.Question
import ru.hse.coursework.godaily.ui.components.superorganisms.CreateRouteView
import ru.hse.coursework.godaily.ui.components.superorganisms.Tutorial
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun CreateRouteMapScreen(
    navController: NavController,
    routeId: String,
    viewModel: CreateRouteViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        MapKitFactory.getInstance().onStart()

        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(routeId) {
        viewModel.loadRouteData(routeId)
    }

    val showTutorial = viewModel.showTutorial

    Box() {
        if (!showTutorial.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Back(
                        onClick = { navController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    HeaderBig(text = "Создание маршрута")

                    Spacer(modifier = Modifier.weight(1f))

                    Question(onClick = { showTutorial.value = true })

                }

                Box {
                    CreateRouteView(
                        showAddPointTitleDialog = viewModel.showAddPointTitleDialog,
                        routePoints = viewModel.routePoints,
                        modifier = Modifier
                            .padding(start = 0.dp, end = 0.dp, top = 16.dp)
                    )

                    ApplyButton(
                        onClick = { navController.navigate(NavigationItem.RouteCreationInfo.route) },
                        text = "Готово!",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 16.dp, bottom = 25.dp)
                    )
                }
            }
        } else {
            Tutorial(
                onFinish = { showTutorial.value = false },
            )
        }
    }

}
