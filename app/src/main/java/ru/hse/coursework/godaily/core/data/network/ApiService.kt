package ru.hse.coursework.godaily.core.data.network

import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.data.model.RouteDTO
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.data.model.RouteSessionDTO
import ru.hse.coursework.godaily.core.data.model.UserDTO

interface ApiService {

    // Регистрация нового пользователя
    suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        userPhoto: String
    ): String // Возвращает JWT

    // Вход в приложение
    suspend fun loginUser(email: String, password: String): String // Возвращает JWT

    // Выход из приложения
    suspend fun logoutUser(): Boolean

    // Получение информации о текущем пользователе
    suspend fun getUserInfo(jwt: String): UserDTO

    // Сохранение маршрута (публикация)
    suspend fun publishRoute(jwt: String, route: RouteDTO): Boolean

    // Сохранение маршрута в черновики
    suspend fun saveRouteToDrafts(jwt: String, route: RouteDTO): Boolean

    // Получение черновиков пользователя
    suspend fun getUserDrafts(jwt: String): List<RouteCardDTO>

    // Получение опубликованных маршрутов пользователя
    suspend fun getUserPublishedRoutes(jwt: String): List<RouteCardDTO>

    // Получение пройденных маршрутов пользователя
    suspend fun getUserCompletedRoutes(jwt: String): List<RouteCardDTO>

    // Получение избранных маршрутов пользователя
    suspend fun getUserFavouriteRoutes(jwt: String): List<RouteCardDTO>

    // Добавление маршрута в избранное
    suspend fun addRouteToFavorites(jwt: String, routeId: String): Boolean

    // Удаление маршрута из избранного
    suspend fun removeRouteFromFavorites(jwt: String, routeId: String): Boolean

    // Удаление маршрута, созданного пользователем
    suspend fun deleteRoute(jwt: String, routeId: String): Boolean

    // Получение подробной информации о маршруте
    suspend fun getRouteDetails(jwt: String, routeId: String): RoutePageDTO

    // Получение отзывов о маршруте
    suspend fun getReviews(jwt: String, routeId: String): List<ReviewDTO>

    // Создание сессии маршрута
    suspend fun createRouteSession(jwt: String, routeSession: RouteSessionDTO): Boolean

    // Обновление полей сессии маршрута
    suspend fun updateRouteSession(jwt: String, routeSession: RouteSessionDTO): Boolean

    // Сохранение отзыва о маршруте
    suspend fun saveReview(jwt: String, routeId: String, review: ReviewDTO): Boolean

    // Сортировка маршрутов по удаленности
    suspend fun getRoutesSortedByDistance(jwt: String, userCoordinate: String): List<RouteCardDTO>

    // Сортировка маршрутов по длине (длинные)
    suspend fun getRoutesSortedByLengthDesc(jwt: String): List<RouteCardDTO>

    // Сортировка маршрутов по длине (короткие)
    suspend fun getRoutesSortedByLengthAsc(jwt: String): List<RouteCardDTO>

    // Сортировка маршрутов по рейтингу
    suspend fun getRoutesSortedByRating(jwt: String): List<RouteCardDTO>

    // Фильтрация маршрутов по категории и сортировка по удаленности
    suspend fun filterRoutesByCategoryAndDistance(
        jwt: String,
        userCoordinate: String,
        category: Category
    ): List<RouteCardDTO>
}