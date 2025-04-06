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
            //TODO хардкод
            userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            routeId = routeId
        )
    }
}