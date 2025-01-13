package ru.hse.coursework.godaily.core.data.network

import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.RouteDTO
import ru.hse.coursework.godaily.core.data.model.UserDTO

interface ApiService {

    // Регистрация нового пользователя
    suspend fun registerUser(
        userName: String,
        email: String,
        password: String,
        userPhoto: String?
    ): UserDTO

    // Вход в приложение
    suspend fun loginUser(email: String, password: String): String

    // Выход пользователя
    suspend fun logoutUser(): Boolean

    // Достать информацию о пользователе
    suspend fun getUserInfo(userId: String): UserDTO

    // Сохранение маршрута (публикация)
    suspend fun publishRoute(route: RouteDTO): Boolean

    // Сохранение маршрута (черновик)
    suspend fun saveRouteToDrafts(route: RouteDTO): Boolean

    // Достать черновики пользователя
    suspend fun getUserDrafts(userId: String): List<RouteDTO>

    // Достать опубликованные маршруты пользователя
    suspend fun getUserPublishedRoutes(userId: String): List<RouteDTO>

    // Достать избранные маршруты пользователя
    suspend fun getUserFavouriteRoutes(userId: String): List<RouteDTO>

    // Достать пройденные маршруты пользовател
    suspend fun getUserCompletedRoutes(userId: String): List<RouteDTO>

    // Добавление маршрута в Избранное
    suspend fun addRouteToFavorites(userId: String, routeId: String): Boolean

    // Удаление маршрута
    suspend fun deleteRoute(routeId: String): Boolean

    // Получение подробной информации о маршруте
    suspend fun getRouteDetails(routeId: String): RouteDTO

    // Создание сессии маршрута
    suspend fun createRouteSession(userId: String, routeId: String): Boolean

    // Сохранение отзыва
    suspend fun saveReview(review: ReviewDTO): Boolean

    // Получение маршрутов, отсортированных по рейтингу
    suspend fun getRoutesSortedByRating(): List<RouteDTO>

    // Фильтрация маршрутов
    suspend fun filterRoutesByCategory(category: String): List<RouteDTO>
}
