package ru.hse.coursework.godaily.screen.routedetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.PassRouteAgainDialog
import ru.hse.coursework.godaily.ui.components.organisms.PauseDialog
import ru.hse.coursework.godaily.ui.components.organisms.RouteFinishDialog
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.components.superorganisms.YandexMapNavigationView
import ru.hse.coursework.godaily.ui.navigation.BottomNavigationItem
import ru.hse.coursework.godaily.ui.notification.ToastManager

@Composable
fun RoutePassingScreen(
    bottomNavController: NavHostController,
    navController: NavController,
    routeId: String,
    viewModel: RouteDetailsViewModel = hiltViewModel()
) {
    val isLoading = viewModel.isLoading

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val showPauseDialog = viewModel.showPauseDialog
    val showFinishRouteDialog = viewModel.showFinishRouteDialog
    val showPassRouteAgainDialog = viewModel.showPassRouteAgainDialog
    val isBackPressed = viewModel.isBackPressed

    val markState = viewModel.userMark
    val reviewTextState = viewModel.reviewText

    val isListenerInitialized = remember { mutableStateOf(false) }

    val listener = remember {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (isListenerInitialized.value && viewModel.passedPoints.isNotEmpty()) {
                coroutineScope.launch {
                    viewModel.saveRouteSession(context)
                }
                ToastManager(context).showToast("Незавершенный маршрут можно найти на главной")
            }
            isListenerInitialized.value = true
        }
    }

    BackHandler(enabled = true) {
        if (viewModel.passedPoints.isEmpty()) {
            navController.popBackStack()
        } else {
            viewModel.showPauseDialog.value = true
            viewModel.isBackPressed.value = true
        }
    }

    DisposableEffect(Unit) {
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

        if (viewModel.isFinished.value) {
            showPassRouteAgainDialog.value = true
        }
    }

    LoadingScreenWrapper(isLoading = isLoading) {
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
                    coroutineScope.launch {
                        viewModel.saveRouteSession(context)
                    }
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
        if (showPassRouteAgainDialog.value) {
            PassRouteAgainDialog(
                showDialog = showPassRouteAgainDialog,
                passAgain = {
                    viewModel.resetRouteSession()
                },
                onHomeClick = {
                    bottomNavController.removeOnDestinationChangedListener(listener)
                    bottomNavController.navigate(BottomNavigationItem.Home.route) {
                        popUpTo(BottomNavigationItem.Home.route) { inclusive = true }
                    }
                }
            )
        }

        if (showPauseDialog.value && !viewModel.passedPoints.isEmpty()) {
            PauseDialog(
                showDialog = showPauseDialog,
                onHomeClick = {
                    coroutineScope.launch {
                        viewModel.saveRouteSession(context)
                    }
                    when {
                        isBackPressed.value -> {
                            isBackPressed.value = false
                            navController.popBackStack()
                        }

                        else -> {
                            bottomNavController.removeOnDestinationChangedListener(listener)
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
                    viewModel.saveReview(context)
                    showFinishRouteDialog.value = false
                    bottomNavController.removeOnDestinationChangedListener(listener)
                    bottomNavController.navigate(BottomNavigationItem.Profile.route) {
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onNotSaveClick = {
                    showFinishRouteDialog.value = false
                    bottomNavController.removeOnDestinationChangedListener(listener)
                    bottomNavController.navigate(BottomNavigationItem.Profile.route) {
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}