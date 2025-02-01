package ru.hse.coursework.godaily.core.domain.routes

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class FetchDraftsUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(): List<RouteCardDto> {
        return api.getUserDrafts("")
    }
}