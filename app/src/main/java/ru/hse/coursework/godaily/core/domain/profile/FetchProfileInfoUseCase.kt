package ru.hse.coursework.godaily.core.domain.profile

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.model.ProfileInfo
import javax.inject.Inject

//TODO: реорганизовать расположение классов
class FetchProfileInfoUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(userId: String): ProfileInfo {
        val userDto = api.getUserInfo(userId)
        val completedRoutes = api.getUserCompletedRoutes(userId)
        val favourites = api.getUserFavouriteRoutes(userId)
        return ProfileInfo(
            email = userDto.email,
            username = userDto.username,
            photoUrl = userDto.photoURL,
            completedRoutes = completedRoutes,
            favouriteRoutes = favourites
        )
    }

}