package ru.hse.coursework.godaily.screen.routes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.routes.FetchCreatedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.routes.FetchDraftsUseCase
import ru.hse.coursework.godaily.core.domain.service.UuidService
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val fetchCreatedRoutesUseCase: FetchCreatedRoutesUseCase,
    private val fetchDraftsUseCase: FetchDraftsUseCase,
    private val uuidService: UuidService
) : ViewModel() {

    init {
        loadRoutesScreenInfo()
    }

    val drafts: SnapshotStateList<RouteCardDto> = mutableStateListOf()
    val publishedRoutes: SnapshotStateList<RouteCardDto> = mutableStateListOf()
    private fun updatePublishedRoutes(routes: List<RouteCardDto>) {
        publishedRoutes.clear()
        publishedRoutes.addAll(routes)
    }

    private fun updateDrafts(routes: List<RouteCardDto>) {
        drafts.clear()
        drafts.addAll(routes)
    }

    private fun loadRoutesScreenInfo() {
        viewModelScope.launch {
            val loadedCreatedRoutes = fetchCreatedRoutesUseCase.execute()
            val loadedDrafts = fetchDraftsUseCase.execute()

            updatePublishedRoutes(loadedCreatedRoutes)
            updateDrafts(loadedDrafts)
        }
    }

    fun getUuid(): UUID {
        return uuidService.getRandomUUID()
    }

}