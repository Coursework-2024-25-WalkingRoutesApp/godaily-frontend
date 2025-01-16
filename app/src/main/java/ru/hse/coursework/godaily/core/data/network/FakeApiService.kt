package ru.hse.coursework.godaily.core.data.network

import ru.hse.coursework.godaily.core.data.model.*
import java.time.LocalDateTime

class FakeApiService : ApiService {

    private val fakeUser = UserDTO("Test User", "testuser@example.com", "fakePhotoUrl")

    private val fakeRoutes = listOf(
        RouteCardDTO("1", "City Tour", 120, 5000, "City Center URL", listOf(Category.Coffee, Category.Nature)),
        RouteCardDTO("2", "Mountain Hike", 240, 8000, "City Center URL", listOf(Category.Metro, Category.Coffee)),
        RouteCardDTO("3", "Beach Walk", 60, 3000, "City Center URL", listOf(Category.Culture, Category.Metro))
    )

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        userPhoto: String
    ): String {
        return "mock_jwt_token"
    }

    override suspend fun loginUser(email: String, password: String): String {
        return "mock_jwt_token"
    }

    override suspend fun logoutUser(): Boolean {
        return true
    }

    override suspend fun getUserInfo(jwt: String): UserDTO {
        return fakeUser
    }

    override suspend fun publishRoute(jwt: String, route: RouteDTO): Boolean {
        return true
    }

    override suspend fun saveRouteToDrafts(jwt: String, route: RouteDTO): Boolean {
        return true
    }

    override suspend fun getUserDrafts(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getUserPublishedRoutes(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getUserCompletedRoutes(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getUserFavouriteRoutes(jwt: String): List<RouteCardDTO> {
        return fakeRoutes.take(2)
    }

    override suspend fun addRouteToFavorites(jwt: String, routeId: String): Boolean {
        return true
    }

    override suspend fun removeRouteFromFavorites(jwt: String, routeId: String): Boolean {
        return true
    }

    override suspend fun deleteRoute(jwt: String, routeId: String): Boolean {
        return true
    }

    override suspend fun getRouteDetails(jwt: String, routeId: String): RouteDTO {
        return RouteDTO(
            routeId, "City Tour Detailed", "Detailed description", 120, 5000,
            "City Center", "City Park", "mockPreviewUrl", false, listOf(), listOf(Category.Coffee, Category.Metro)
        )
    }

    override suspend fun getReviews(jwt: String, routeId: String): List<ReviewDTO> {
        return listOf(
            ReviewDTO("User1", routeId, 5, "Great route!",  LocalDateTime.now()),
            ReviewDTO("User2", routeId, 4,"Loved it!", LocalDateTime.now())
        )
    }

    override suspend fun createRouteSession(jwt: String, routeSession: RouteSessionDTO): Boolean {
        return true
    }

    override suspend fun updateRouteSession(jwt: String, routeSession: RouteSessionDTO): Boolean {
        return true
    }

    override suspend fun saveReview(jwt: String, routeId: String, review: ReviewDTO): Boolean {
        return true
    }

    override suspend fun getRoutesSortedByDistance(jwt: String, userCoordinate: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getRoutesSortedByLengthDesc(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getRoutesSortedByLengthAsc(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun getRoutesSortedByRating(jwt: String): List<RouteCardDTO> {
        return fakeRoutes
    }

    override suspend fun filterRoutesByCategoryAndDistance(
        jwt: String,
        userCoordinate: String,
        category: Category
    ): List<RouteCardDTO> {
        return fakeRoutes
    }
}
