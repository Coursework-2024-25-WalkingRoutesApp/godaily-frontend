package ru.hse.coursework.godaily.core.domain.home

import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchUnfinishedRoutesUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(): List<RouteCardDTO> {
        return api.getUserUnfinishedRoutes("")
    }
}