package ru.hse.coursework.godaily.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesBySearchValue
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesForHomeScreenUseCase
import ru.hse.coursework.godaily.core.domain.home.FetchUnfinishedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.home.FilterRoutesUseCase
import ru.hse.coursework.godaily.core.domain.home.SortRoutesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRoutesForHomeScreenUseCase: FetchRoutesForHomeScreenUseCase,
    private val fetchRoutesBySearchValue: FetchRoutesBySearchValue,
    private val fetchUnfinishedRoutesUseCase: FetchUnfinishedRoutesUseCase,
    private val filterRoutesUseCase: FilterRoutesUseCase,
    private val sortRoutesUseCase: SortRoutesUseCase
) : ViewModel() {

    val routesForGrid: SnapshotStateList<RouteCardDto> = mutableStateListOf()
    val unfinishedRoutes: SnapshotStateList<RouteCardDto> = mutableStateListOf()
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

    fun loadHomeScreenInfo() {
        viewModelScope.launch {
            val loadedUnfinishedRoutes = fetchUnfinishedRoutesUseCase.execute()
            val loadedRoutesForGrid = fetchRoutesForHomeScreenUseCase.execute()

            updateRoutesForGrid(loadedRoutesForGrid)
            updateUnfinishedRoutes(loadedUnfinishedRoutes)
        }
    }

    fun filterRoutes() {
        //TODO фильтры сбрасываются, когда поиск по названию
        viewModelScope.launch {
            updateRoutesForGrid(filterRoutesUseCase.execute(selectedCategories.value))
        }
        sortRoutes()
    }

    fun sortRoutes() {
        viewModelScope.launch {
            updateRoutesForGrid(sortRoutesUseCase.execute(routesForGrid, selectedSortOption.value))
        }
    }

    fun searchRoutes() {
        //TODO возможный сброс сортировки и фильтров
        viewModelScope.launch {
            updateRoutesForGrid(fetchRoutesBySearchValue.execute(searchValue.value))
        }
    }


}