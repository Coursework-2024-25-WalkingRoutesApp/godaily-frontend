package ru.hse.coursework.godaily.core.domain.routes

import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchCreatedRoutesUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(): List<RouteCardDTO> {
        return api.getUserPublishedRoutes("")
    }
}