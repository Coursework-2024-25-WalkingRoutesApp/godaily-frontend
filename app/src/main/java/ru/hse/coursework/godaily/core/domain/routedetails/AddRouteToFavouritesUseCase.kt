package ru.hse.coursework.godaily.core.domain.routedetails

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import java.util.UUID
import javax.inject.Inject

class AddRouteToFavouritesUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        routeId: UUID,
    ): Response<String> {
        return api.addRouteToFavorites(
            "",
            routeId
        )
    }
}