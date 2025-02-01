package ru.hse.coursework.godaily.screen.routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.routes.FetchCreatedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.routes.FetchDraftsUseCase
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val fetchCreatedRoutesUseCase: FetchCreatedRoutesUseCase,
    private val fetchDraftsUseCase: FetchDraftsUseCase
) : ViewModel() {

    val drafts: MutableList<RouteCardDto> = mutableListOf()
    val publishedRoutes: MutableList<RouteCardDto> = mutableListOf()

    //TODO: подумать над оптимальными функциями
    fun updatePublishedRoutes(routes: List<RouteCardDto>) {
        publishedRoutes.clear()
        publishedRoutes.addAll(routes)
    }

    fun updateDrafts(routes: List<RouteCardDto>) {
        drafts.clear()
        drafts.addAll(routes)
    }

    fun loadRoutesScreenInfo() {
        viewModelScope.launch {
            val loadedCreatedRoutes = fetchCreatedRoutesUseCase.execute()
            val loadedDrafts = fetchDraftsUseCase.execute()

            updatePublishedRoutes(loadedCreatedRoutes)
            updateDrafts(loadedDrafts)
        }
    }

}