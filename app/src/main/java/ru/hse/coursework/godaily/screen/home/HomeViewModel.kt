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
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesBySearchValue
import ru.hse.coursework.godaily.core.domain.home.FetchRoutesForHomeScreenUseCase
import ru.hse.coursework.godaily.core.domain.home.FetchUnfinishedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.home.FilterRoutesUseCase
import ru.hse.coursework.godaily.core.domain.home.SortRoutesUseCase
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRoutesForHomeScreenUseCase: FetchRoutesForHomeScreenUseCase,
    private val fetchRoutesBySearchValue: FetchRoutesBySearchValue,
    private val fetchUnfinishedRoutesUseCase: FetchUnfinishedRoutesUseCase,
    private val filterRoutesUseCase: FilterRoutesUseCase,
    private val sortRoutesUseCase: SortRoutesUseCase,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val routesForGrid: SnapshotStateList<RouteCardDto> = mutableStateListOf()
    val unfinishedRoutes: SnapshotStateList<RouteCardDto> = mutableStateListOf()
    val searchValue: MutableState<String> = mutableStateOf("")
    val selectedCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedSortOption: MutableState<Int> = mutableStateOf(0)
    val showFilterSheet = mutableStateOf(false)
    val showSortSheet = mutableStateOf(false)
    val chosenSortOptionText = mutableStateOf("Ближе ко мне")

    private fun updateRoutesForGrid(routes: List<RouteCardDto>) {
        routesForGrid.clear()
        routesForGrid.addAll(routes)
    }

    private fun updateUnfinishedRoutes(routes: List<RouteCardDto>) {
        unfinishedRoutes.clear()
        unfinishedRoutes.addAll(routes)
    }

    fun loadHomeScreenInfo() {
        viewModelScope.launch {
            val loadedUnfinishedRoutesResponse = fetchUnfinishedRoutesUseCase.execute()
            val loadedRoutesForGridResponse = fetchRoutesForHomeScreenUseCase.execute()

            when (loadedUnfinishedRoutesResponse) {
                is ApiCallResult.Error -> errorHandler.handleError(loadedUnfinishedRoutesResponse)
                is ApiCallResult.Success -> updateUnfinishedRoutes(loadedUnfinishedRoutesResponse.data)
            }

            when (loadedRoutesForGridResponse) {
                is ApiCallResult.Error -> errorHandler.handleError(loadedRoutesForGridResponse)
                is ApiCallResult.Success -> updateRoutesForGrid(loadedRoutesForGridResponse.data)
            }
        }
    }

    fun filterRoutes(selected: Set<Int>) {
        searchValue.value = ""

        viewModelScope.launch {
            val filteredRoutesResponse = filterRoutesUseCase.execute(selected)
            when (filteredRoutesResponse) {
                is ApiCallResult.Error -> errorHandler.handleError(filteredRoutesResponse)
                is ApiCallResult.Success -> updateRoutesForGrid(filteredRoutesResponse.data)
            }
        }
        sortRoutes()
    }

    fun sortRoutes() {
        viewModelScope.launch {
            updateRoutesForGrid(sortRoutesUseCase.execute(routesForGrid, selectedSortOption.value))
        }
    }

    fun searchRoutes() {
        selectedCategories.value = setOf()
        selectedSortOption.value = 0
        //TODO хардкод
        chosenSortOptionText.value = "Ближе ко мне"

        viewModelScope.launch {
            val searchedRoutesResponse = fetchRoutesBySearchValue.execute(searchValue.value)
            when (searchedRoutesResponse) {
                is ApiCallResult.Error -> errorHandler.handleError(searchedRoutesResponse)
                is ApiCallResult.Success -> updateRoutesForGrid(searchedRoutesResponse.data)
            }
        }
    }


}