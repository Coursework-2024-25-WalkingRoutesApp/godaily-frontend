package ru.hse.coursework.godaily.core.domain.profile

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

//TODO путаница с jwt и ID
class FetchProfileInfoUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(): ApiCallResult<Any> {
        //TODO хардкод
        val userDtoResponse = safeApiCaller.safeApiCall { api.getUserInfo("") }
        if (userDtoResponse !is ApiCallResult.Success) {
            return userDtoResponse
        }

        val userLocation = locationService.getUserCoordinate()

        val completedRoutesResponse =
            safeApiCaller.safeApiCall {
                api.getUserCompletedRoutes(
                    UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                    userLocation.latitude,
                    userLocation.longitude
                )
            }
        if (completedRoutesResponse !is ApiCallResult.Success) {
            return completedRoutesResponse
        }

        val favouritesResponse =
            safeApiCaller.safeApiCall {
                api.getUserFavouriteRoutes(
                    UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                    userLocation.latitude,
                    userLocation.longitude
                )
            }

        if (favouritesResponse !is ApiCallResult.Success) {
            return favouritesResponse
        }

        return ApiCallResult.Success(
            ProfileInfo(
                email = userDtoResponse.data.email,
                username = userDtoResponse.data.username,
                photoUrl = userDtoResponse.data.photoURL,
                completedRoutes = completedRoutesResponse.data,
                favouriteRoutes = favouritesResponse.data
            )
        )
    }
}

data class ProfileInfo(
    val email: String,
    val username: String,
    val photoUrl: String?,
    val completedRoutes: List<RouteCardDto> = emptyList(),
    val favouriteRoutes: List<RouteCardDto> = emptyList()
)