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

    // Управление видимостью BottomSheet
    val showFilterSheet = remember { mutableStateOf(false) }
    val showSortSheet = remember { mutableStateOf(false) }

    // Состояние выбранных фильтров и сортировки
    val selectedFilters = remember { mutableStateOf(setOf<Int>()) }
    val selectedSort = remember { mutableStateOf(0) }

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

            // SearchToolbar с кнопками фильтров и сортировки
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchToolbar(
                    searchValue = searchValue,
                    filterIconClick = { showFilterSheet.value = true }, // Открываем BottomSheet фильтров
                    sortOptions = listOf(
                        SortOption.CLOSER_TO_ME,
                        SortOption.HIGH_RATING,
                        SortOption.LONG,
                        SortOption.SHORT
                    ),
                    onSortOptionSelected = { showSortSheet.value = true } // Открываем BottomSheet сортировки
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

    // **Фильтр BottomSheet**
    if (showFilterSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet.value = false }
        ) {
            /*SortBottomSheet(
                selectedItems = selectedFilters,
                onClose = { showFilterSheet.value = false },
                onApply = { selected ->
                    selectedFilters.value = selected
                    showFilterSheet.value = false
                },
                onReset = { selectedFilters.value = emptySet() }
            )*/
        }
    }

    // **Сортировка BottomSheet**
    if (showSortSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSortSheet.value = false }
        ) {
            /*SortBottomSheetSingleChoice(
                selectedOption = selectedSort.value,
                onClose = { showSortSheet.value = false },
                onApply = { selected ->
                    selectedSort.value = selected
                    showSortSheet.value = false
                },
                onReset = { selectedSort.value = 0 }
            )*/
        }
    }
}
