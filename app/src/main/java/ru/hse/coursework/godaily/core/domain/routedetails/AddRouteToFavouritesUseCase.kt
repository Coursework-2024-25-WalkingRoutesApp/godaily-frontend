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
            api.addRouteToFavorites(routeId)
        }
    }
}