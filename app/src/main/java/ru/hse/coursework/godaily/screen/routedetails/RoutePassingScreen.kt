package ru.hse.coursework.godaily.screen.routedetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.yandex.mapkit.MapKitFactory
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.PauseDialog
import ru.hse.coursework.godaily.ui.components.organisms.RouteFinishDialog
import ru.hse.coursework.godaily.ui.components.superorganisms.YandexMapNavigationView
import ru.hse.coursework.godaily.ui.navigation.BottomNavigationItem
import ru.hse.coursework.godaily.ui.notification.ToastManager

//TODO обработка случая, если маршрут был завершен и его можно пройти заново
@Composable
fun RoutePassingScreen(
    bottomNavController: NavHostController,
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val showPauseDialog = viewModel.showPauseDialog
    val showFinishRouteDialog = viewModel.showFinishRouteDialog
    val isBackPressed = viewModel.isBackPressed

    val markState = viewModel.userMark
    val reviewTextState = viewModel.reviewText

    BackHandler(enabled = true) {
        if (viewModel.passedPoints.isEmpty()) {
            navController.popBackStack()
        } else {
            viewModel.showPauseDialog.value = true
            viewModel.isBackPressed.value = true
        }
    }

    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (!viewModel.passedPoints.isEmpty()) {
                //TODO сохранить прогресс
                ToastManager(context).showToast("Незавершенный маршрут можно найти на главной")
            }
        }

        bottomNavController.addOnDestinationChangedListener(listener)

        MapKitFactory.getInstance().onStart()

        onDispose {
            bottomNavController.removeOnDestinationChangedListener(listener)
            MapKitFactory.getInstance().onStop()
        }
    }

    LaunchedEffect(routeId) {
        viewModel.loadRouteDetails(routeId)
        viewModel.loadSessionPoints(routeId)
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        YandexMapNavigationView(
            routeTitle = viewModel.route.value.routeName ?: "Маршрут",
            routePoints = viewModel.routePoints,
            passedPoints = viewModel.passedPoints,
            viewModel = viewModel,
            onPauseClick = {
                showPauseDialog.value = true
            },
            onFinishClick = {
                showFinishRouteDialog.value = true
            }
        )
        Back(
            onClick = {
                if (viewModel.passedPoints.isEmpty()) {
                    navController.popBackStack()
                } else {
                    showPauseDialog.value = true
                    isBackPressed.value = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }

    if (showPauseDialog.value && !viewModel.passedPoints.isEmpty()) {
        PauseDialog(
            showDialog = showPauseDialog,
            onHomeClick = {
                when {
                    isBackPressed.value -> {
                        isBackPressed.value = false
                        navController.popBackStack()
                    }

                    else -> {
                        bottomNavController.navigate(BottomNavigationItem.Home.route) {
                            popUpTo(BottomNavigationItem.Home.route) { inclusive = true }
                        }
                    }
                }
            }
        )
    }

    if (showFinishRouteDialog.value) {
        RouteFinishDialog(
            showDialog = showFinishRouteDialog,
            mark = markState,
            feedbackText = reviewTextState,
            onSaveClick = {
                /*TODO*/
                showFinishRouteDialog.value = false
            },
            onNotSaveClick = { /*TODO*/
                showFinishRouteDialog.value = false
            })
    }

}