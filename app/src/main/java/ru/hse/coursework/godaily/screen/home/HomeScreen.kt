package ru.hse.coursework.godaily.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.core.data.model.SortOption
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.organisms.SearchToolbar
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteToContinueGrid
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.components.organisms.SortBottomSheet
import ru.hse.coursework.godaily.ui.components.organisms.SortBottomSheetSingleChoice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
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

    //TODO: координаты
    viewModel.loadHomeScreenInfo("")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderWithBackground(header = "Маршруты для вас")

        if (routesForGrid.isEmpty() && unfinishedRoutes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                VariableMedium(
                    text = "Пока нет таких маршрутов",
                    fontSize = 16.sp,
                    fontColor = greyDark
                )
            }
        } else {
            if (unfinishedRoutes.isNotEmpty()) {
                RouteToContinueGrid(routes = unfinishedRoutes)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                //TODO: подумать над багом с выбором и применением
                SearchToolbar(
                    searchValue = searchValue,
                    filterIconClick = { showFilterSheet.value = true },
                    sortClick = { showSortSheet.value = true },
                    chosenSortOption = chosenSortOptionText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            RouteVerticalGrid(
                routes = routesForGrid,
                onRouteClick = { route ->
                    navController.navigate(NavigationItem.RouteDetails.route + "/${route.id}")
                }
            )
        }
    }

    if (showFilterSheet.value) {
        SortBottomSheet(
            showFilterSheet = showFilterSheet,
            selectedItems = selectedCategories,
            onApply = { selected ->
                selectedCategories.value = selected
                showFilterSheet.value = false
                //TODO: обновление маршрутов
            },
            onReset = { selectedCategories.value = emptySet() }
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
                //TODO: обновление маршрутов
            },
            onReset = { selectedSortOption.value = 0 }
        )
    }
}
