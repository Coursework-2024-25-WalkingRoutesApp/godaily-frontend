package ru.hse.coursework.godaily.screen.routes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.routes.FetchCreatedRoutesUseCase
import ru.hse.coursework.godaily.core.domain.routes.FetchDraftsUseCase
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.core.tracking.TrackingService
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val fetchCreatedRoutesUseCase: FetchCreatedRoutesUseCase,
    private val fetchDraftsUseCase: FetchDraftsUseCase,
    private val uuidService: UuidService,
    private val errorHandler: ErrorHandler,
    private val trackingService: TrackingService,
    val imageLoader: ImageLoader
) : ViewModel() {

    val isLoading = mutableStateOf(false)

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
            isLoading.value = true

            val loadedCreatedRoutesResponse = fetchCreatedRoutesUseCase.execute()
            val loadedDraftsResponse = fetchDraftsUseCase.execute()
            var loadedCreatedRoutes = emptyList<RouteCardDto>()
            var loadedDrafts = emptyList<RouteCardDto>()

            when (loadedCreatedRoutesResponse) {
                is ApiCallResult.Error -> {
                    errorHandler.handleError(loadedCreatedRoutesResponse)
                }

                is ApiCallResult.Success -> {
                    loadedCreatedRoutes = loadedCreatedRoutesResponse.data
                }
            }

            when (loadedDraftsResponse) {
                is ApiCallResult.Error -> {
                    errorHandler.handleError(loadedDraftsResponse)
                }

                is ApiCallResult.Success -> {
                    loadedDrafts = loadedDraftsResponse.data
                }
            }

            updatePublishedRoutes(loadedCreatedRoutes)
            updateDrafts(loadedDrafts)
            isLoading.value = false
        }
    }

    fun trackRouteDetailsOpen(routeId: UUID?, routeName: String?) {
        trackingService.trackRouteDetailsOpen(routeId, routeName)
    }

}