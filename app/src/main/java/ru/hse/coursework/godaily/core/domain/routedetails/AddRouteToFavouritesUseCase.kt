package ru.hse.coursework.godaily.core.domain.routedetails

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import java.util.UUID
import javax.inject.Inject

class AddRouteToFavouritesUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        routeId: UUID,
    ): ApiCallResult<String> {
        return safeApiCaller.safeApiCall {
            api.addRouteToFavorites(
                //TODO хардкод
                userId = UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                routeId = routeId
            )
        }
    }
}