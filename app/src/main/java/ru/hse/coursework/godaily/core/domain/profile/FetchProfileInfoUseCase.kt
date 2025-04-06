package ru.hse.coursework.godaily.core.domain.profile

import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import java.util.UUID
import javax.inject.Inject

//TODO путаница с jwt и ID
class FetchProfileInfoUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(): ProfileInfo {
        //TODO хардкод
        val userDto = api.getUserInfo("")
        val userLocation = locationService.getUserCoordinate()
        val completedRoutes =
            api.getUserCompletedRoutes(
                UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
                userLocation.latitude,
                userLocation.longitude
            )
        val favourites = api.getUserFavouriteRoutes(
            UUID.fromString("a0bd4f18-d19c-4d79-b9b7-03108f990412"),
            userLocation.latitude,
            userLocation.longitude
        )
        return ProfileInfo(
            email = userDto.email,
            username = userDto.username,
            photoUrl = userDto.photoURL,
            completedRoutes = completedRoutes,
            favouriteRoutes = favourites
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