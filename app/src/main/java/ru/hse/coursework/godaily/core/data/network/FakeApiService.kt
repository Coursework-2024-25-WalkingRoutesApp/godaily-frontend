package ru.hse.coursework.godaily.core.data.network


import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.RouteDTO
import ru.hse.coursework.godaily.core.data.model.UserDTO

class FakeApiService : ApiService {

    // Регистрация нового пользователя
    override suspend fun registerUser(
        userName: String,
        email: String,
        password: String,
        userPhoto: String
    ): UserDTO {
        return UserDTO(
            id = "1",
            userName = userName,
            email = email,
            userPhoto = userPhoto
        )
    }

    // Вход в приложение
    override suspend fun loginUser(email: String, password: String): String {
        return "mock_token_123"
    }

    // Выход пользователя
    override suspend fun logoutUser(): Boolean {
        return true
    }

    // Достать информацию о пользователе
    override suspend fun getUserInfo(userId: String): UserDTO {
        return UserDTO(
            id = userId,
            userName = "Test User",
            email = "testuser@example.com",
            userPhoto = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG3RRs0ouk80nISkSjBII8TgTshOBcitVnJg&s"
        )
    }

    // Сохранение маршрута (публикация)
    override suspend fun publishRoute(route: RouteDTO): Boolean {
        return true
    }

    // Сохранение маршрута (черновик)
    override suspend fun saveRouteToDrafts(route: RouteDTO): Boolean {
        return true
    }

    // Достать черновики пользователя
    override suspend fun getUserDrafts(userId: String): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "101",
                userId = userId,
                routeName = "Mock Draft Route",
                description = "This is a draft route",
                duration = 60,
                length = 5000,
                startPoint = "Start Point",
                endPoint = "End Point",
                routePreview = "https://example.com/route_preview.jpg",
                isDraft = true,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            )
        )
    }

    // Достать опубликованные маршруты пользователя
    override suspend fun getUserPublishedRoutes(userId: String): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "102",
                userId = userId,
                routeName = "Published Route",
                description = "This is a published route",
                duration = 90,
                length = 8000,
                startPoint = "City Center",
                endPoint = "Park",
                routePreview = "https://example.com/published_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            )
        )
    }

    // Достать избранные маршруты пользователя
    override suspend fun getUserFavouriteRoutes(userId: String): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "103",
                userId = userId,
                routeName = "Favourite Route",
                description = "This is a favourite route",
                duration = 120,
                length = 10000,
                startPoint = "Station",
                endPoint = "Beach",
                routePreview = "https://example.com/favourite_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            )
        )
    }

    // Достать пройденные маршруты пользователя
    override suspend fun getUserCompletedRoutes(userId: String): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
            RouteDTO(
                id = "104",
                userId = userId,
                routeName = "Completed Route",
                description = "This is a completed route",
                duration = 45,
                length = 3000,
                startPoint = "Mountain Base",
                endPoint = "Summit",
                routePreview = "https://example.com/completed_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            ),
        )
    }

    // Добавление маршрута в Избранное
    override suspend fun addRouteToFavorites(userId: String, routeId: String): Boolean {
        return true
    }

    // Удаление маршрута
    override suspend fun deleteRoute(routeId: String): Boolean {
        return true
    }

    // Получение подробной информации о маршруте
    override suspend fun getRouteDetails(routeId: String): RouteDTO {
        return RouteDTO(
            id = routeId,
            userId = "1",
            routeName = "Detailed Route",
            description = "Detailed information about this route",
            duration = 120,
            length = 10000,
            startPoint = "City Center",
            endPoint = "Park",
            routePreview = "https://example.com/route_details.jpg",
            isDraft = false,
            lastModifiedAt = "2025-01-12",
            createdAt = "2025-01-01"
        )
    }

    // Создание сессии маршрута
    override suspend fun createRouteSession(userId: String, routeId: String): Boolean {
        return true
    }

    // Сохранение отзыва
    override suspend fun saveReview(review: ReviewDTO): Boolean {
        return true
    }

    // Получение маршрутов, отсортированных по рейтингу
    override suspend fun getRoutesSortedByRating(): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "105",
                userId = "1",
                routeName = "Top Rated Route",
                description = "A route with the highest ratings",
                duration = 180,
                length = 15000,
                startPoint = "Museum",
                endPoint = "Lake",
                routePreview = "https://example.com/top_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            )
        )
    }

    // Фильтрация маршрутов
    override suspend fun filterRoutesByCategory(category: String): List<RouteDTO> {
        return listOf(
            RouteDTO(
                id = "106",
                userId = "2",
                routeName = "Filtered Route",
                description = "A route filtered by category $category",
                duration = 60,
                length = 4000,
                startPoint = "Market",
                endPoint = "Hill",
                routePreview = "https://example.com/filtered_route.jpg",
                isDraft = false,
                lastModifiedAt = "2025-01-12",
                createdAt = "2025-01-01"
            )
        )
    }
}
