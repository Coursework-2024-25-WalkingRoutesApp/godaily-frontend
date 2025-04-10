package ru.hse.coursework.godaily.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.organisms.NoRoutesBox
import ru.hse.coursework.godaily.ui.components.organisms.SearchToolbar
import ru.hse.coursework.godaily.ui.components.organisms.SortBottomSheet
import ru.hse.coursework.godaily.ui.components.organisms.SortBottomSheetSingleChoice
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteToContinueGrid
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun HomeScreen(
    navController: NavController,
    routeDetailsViewModel: RouteDetailsViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val routesForGrid = viewModel.routesForGrid
    val unfinishedRoutes = viewModel.unfinishedRoutes
    val searchValue = viewModel.searchValue
    val selectedCategories = viewModel.selectedCategories
    val selectedSortOption = viewModel.selectedSortOption
    val showFilterSheet = viewModel.showFilterSheet
    val showSortSheet = viewModel.showSortSheet
    val chosenSortOptionText = viewModel.chosenSortOptionText

    LaunchedEffect(Unit) {
        viewModel.loadHomeScreenInfo()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderWithBackground(header = "Маршруты для вас")

        if (unfinishedRoutes.isNotEmpty()) {
            RouteToContinueGrid(
                routes = unfinishedRoutes,
                onRouteClick = { route ->
                    viewModel.trackRouteDetailsOpen(route.id, route.routeName)
                    routeDetailsViewModel.clear()
                    navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SearchToolbar(
                searchValue = searchValue,
                onSearchValueChange = {
                    searchValue.value = it
                    viewModel.searchRoutes()
                },
                filterIconClick = { showFilterSheet.value = true },
                sortClick = { showSortSheet.value = true },
                chosenSortOption = chosenSortOptionText
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (routesForGrid.isEmpty()) {
            NoRoutesBox()
        }

        RouteVerticalGrid(
            routes = routesForGrid,
            onRouteClick = { route ->
                viewModel.trackRouteDetailsOpen(route.id, route.routeName)
                routeDetailsViewModel.clear()
                navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
            }
        )
    }

    if (showFilterSheet.value) {
        SortBottomSheet(
            showFilterSheet = showFilterSheet,
            selectedItems = selectedCategories,
            onApply = { selected ->
                selectedCategories.value = selected
                showFilterSheet.value = false
                viewModel.filterRoutes()
                viewModel.sortRoutes()
            },
            onReset = {
                selectedCategories.value = emptySet()
                viewModel.filterRoutes()
            }
        )
    }

    if (showSortSheet.value) {
        SortBottomSheetSingleChoice(
            showSortSheet = showSortSheet,
            selectedOption = selectedSortOption,
            chosenSortOptionText = chosenSortOptionText,
            onApply = { selected ->
                selectedSortOption.value = selected
                showSortSheet.value = false
                viewModel.sortRoutes()
            },
            onReset = {
                selectedSortOption.value = 0
                viewModel.sortRoutes()
            }
        )
    }
}
