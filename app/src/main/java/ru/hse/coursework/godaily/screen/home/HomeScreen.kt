package ru.hse.coursework.godaily.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.data.model.SortOption
import ru.hse.coursework.godaily.screen.profile.ProfileViewModel
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.organisms.CompletedRoutes
import ru.hse.coursework.godaily.ui.components.organisms.FavouriteRoutes
import ru.hse.coursework.godaily.ui.components.organisms.SearchToolbar
import ru.hse.coursework.godaily.ui.components.organisms.UserProfile
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteToContinueGrid
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteVerticalGrid
import ru.hse.coursework.godaily.ui.navigation.NavigationItem
import ru.hse.coursework.godaily.ui.theme.greyDark

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

    //TODO: поиск координаты
    viewModel.loadHomeScreenInfo("")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        HeaderWithBackground(header = "Маршруты для вас")

        if (routesForGrid.isEmpty() && unfinishedRoutes.isEmpty()) {
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
            if (unfinishedRoutes.isNotEmpty()) {
                RouteToContinueGrid(routes = unfinishedRoutes)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchToolbar(
                    searchValue = mutableStateOf(""),
                    //onSearchValueChange = ,
                    filterIconClick = { /*TODO*/ },
                    sortOptions = listOf(SortOption.CLOSER_TO_ME, SortOption.HIGH_RATING, SortOption.LONG, SortOption.SHORT),
                    onSortOptionSelected = {/*TODO*/}
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
}