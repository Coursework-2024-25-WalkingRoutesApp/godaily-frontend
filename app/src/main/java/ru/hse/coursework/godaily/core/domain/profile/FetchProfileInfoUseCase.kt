package ru.hse.coursework.godaily.core.domain.profile

import com.fasterxml.jackson.databind.ObjectMapper
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.UserDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.location.LocationService
import javax.inject.Inject

class FetchProfileInfoUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService,
    private val safeApiCaller: SafeApiCaller,
    private val objectMapper: ObjectMapper
) {
    suspend fun execute(): ApiCallResult<Any> {
        //TODO хардкод
        //TODO переписать потому что неправильно (см check jwt
        val userDtoResponse = safeApiCaller.safeApiCall { api.getUserInfo() }
        if (userDtoResponse !is ApiCallResult.Success) {
            return userDtoResponse
        }

        val userLocation = locationService.getUserCoordinate()

        val completedRoutesResponse =
            safeApiCaller.safeApiCall {
                api.getUserCompletedRoutes(
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
                    userLocation.latitude,
                    userLocation.longitude
                )
            }

        if (favouritesResponse !is ApiCallResult.Success) {
            return favouritesResponse
        }

        return try {
            val curUserDtoResponse = ApiCallResult.Success(
                objectMapper.convertValue(
                    userDtoResponse.data,
                    UserDto::class.java
                )
            )
            ApiCallResult.Success(
                ProfileInfo(
                    email = curUserDtoResponse.data.email,
                    username = curUserDtoResponse.data.username,
                    photoUrl = curUserDtoResponse.data.photoURL,
                    completedRoutes = completedRoutesResponse.data,
                    favouriteRoutes = favouritesResponse.data
                )
            )
        } catch (e: Exception) {
            return ApiCallResult.Success(
                ProfileInfo(
                    email = "email",
                    username = "Имя пользователя",
                    photoUrl = "",
                    completedRoutes = completedRoutesResponse.data,
                    favouriteRoutes = favouritesResponse.data
                )
            )
        }
    }
}

data class ProfileInfo(
    val email: String,
    val username: String,
    val photoUrl: String?,
    val completedRoutes: List<RouteCardDto> = emptyList(),
    val favouriteRoutes: List<RouteCardDto> = emptyList()
)