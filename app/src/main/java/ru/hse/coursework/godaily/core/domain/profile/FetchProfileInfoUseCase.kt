package ru.hse.coursework.godaily.core.domain.profile

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.location.LocationService
import ru.hse.coursework.godaily.core.domain.model.ProfileInfo
import javax.inject.Inject

//TODO: реорганизовать расположение классов
//TODO путаница с jwt и ID
class FetchProfileInfoUseCase @Inject constructor(
    private val api: ApiService,
    private val locationService: LocationService
) {
    suspend fun execute(userId: String): ProfileInfo {
        val userDto = api.getUserInfo(userId)
        val completedRoutes =
            api.getUserCompletedRoutes(userId, locationService.getUserCoordinate())
        val favourites = api.getUserFavouriteRoutes(userId, locationService.getUserCoordinate())
        return ProfileInfo(
            email = userDto.email,
            username = userDto.username,
            photoUrl = userDto.photoURL,
            completedRoutes = completedRoutes,
            favouriteRoutes = favourites
        )
    }

}