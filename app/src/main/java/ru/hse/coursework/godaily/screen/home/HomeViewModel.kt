package ru.hse.coursework.godaily.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.SortOption
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesBySearchValue
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesForHomeScreenUseCase
import ru.hse.coursework.godaily.core.domain.home.FetchUnfinishedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.home.FilterRoutesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRoutesForHomeScreenUseCase: FetchRoutesForHomeScreenUseCase,
    private val fetchRoutesBySearchValue: FetchRoutesBySearchValue,
    private val fetchUnfinishedRoutesUseCase: FetchUnfinishedRoutesUseCase,
    private val filterRoutesUseCase: FilterRoutesUseCase
) : ViewModel() {

    val routesForGrid: MutableList<RouteCardDto> = mutableListOf()
    val unfinishedRoutes: MutableList<RouteCardDto> = mutableListOf()
    val searchValue: MutableState<String> = mutableStateOf("")
    val selectedCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedSortOption: MutableState<Int> = mutableStateOf(0)
    val showFilterSheet = mutableStateOf(false)
    val showSortSheet = mutableStateOf(false)
    val chosenSortOptionText = mutableStateOf("Ближе ко мне")

    fun updateRoutesForGrid(routes: List<RouteCardDto>) {
        routesForGrid.clear()
        routesForGrid.addAll(routes)
    }

    fun updateUnfinishedRoutes(routes: List<RouteCardDto>) {
        unfinishedRoutes.clear()
        unfinishedRoutes.addAll(routes)
    }

    fun updateSearchValue(text: String) {
        searchValue.value = text
    }

    fun loadHomeScreenInfo(userCoordinate: String) {
        viewModelScope.launch {
            val loadedUnfinishedRoutes = fetchUnfinishedRoutesUseCase.execute()
            val loadedRoutesForGrid = fetchRoutesForHomeScreenUseCase.execute(userCoordinate)

            updateRoutesForGrid(loadedRoutesForGrid)
            updateUnfinishedRoutes(loadedUnfinishedRoutes)
        }
    }

}