package ru.hse.coursework.godaily.core.data.network

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.ReviewPublishDto
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import ru.hse.coursework.godaily.core.data.model.UserDto
import java.util.UUID

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
    suspend fun getUserInfo(jwt: String): UserDto

    // Сохранение маршрута
    suspend fun addRoute(jwt: String, route: RouteDto): Response<String>

    // Получение черновиков пользователя
    suspend fun getUserDrafts(jwt: String): List<RouteCardDto>

    // Получение опубликованных маршрутов пользователя
    suspend fun getUserPublishedRoutes(jwt: String): List<RouteCardDto>

    // Получение пройденных маршрутов пользователя
    suspend fun getUserCompletedRoutes(jwt: String): List<RouteCardDto>

    // Получение избранных маршрутов пользователя
    suspend fun getUserFavouriteRoutes(jwt: String): List<RouteCardDto>

    suspend fun getUserUnfinishedRoutes(jwt: String): List<RouteCardDto>

    // Добавление маршрута в избранное
    suspend fun addRouteToFavorites(jwt: String, routeId: UUID): Response<String>

    // Удаление маршрута из избранного
    suspend fun removeRouteFromFavorites(jwt: String, routeId: UUID): Response<String>

    // Удаление маршрута, созданного пользователем
    suspend fun deleteRoute(jwt: String, routeId: UUID): Boolean

    // Получение подробной информации о маршруте
    suspend fun getRouteDetails(jwt: String, routeId: UUID): RoutePageDto

    // Получение отзывов о маршруте
    suspend fun getReviews(jwt: String, routeId: UUID): ReviewDto

    // Создание сессии маршрута
    suspend fun createRouteSession(jwt: String, routeSession: RouteSessionDto): Boolean

    // Обновление полей сессии маршрута
    suspend fun updateRouteSession(jwt: String, routeSession: RouteSessionDto): Boolean

    // Сохранение отзыва о маршруте
    suspend fun saveReview(jwt: String, review: ReviewPublishDto): Response<String>

    // Сортировка маршрутов по удаленности
    suspend fun getRoutesSortedByDistance(jwt: String, userCoordinate: String): List<RouteCardDto>

    // Сортировка маршрутов по длине (длинные)
    suspend fun getRoutesSortedByLengthDesc(jwt: String): List<RouteCardDto>

    // Сортировка маршрутов по длине (короткие)
    suspend fun getRoutesSortedByLengthAsc(jwt: String): List<RouteCardDto>

    // Сортировка маршрутов по рейтингу
    suspend fun getRoutesSortedByRating(jwt: String): List<RouteCardDto>

    // Фильтрация маршрутов по категории и сортировка по удаленности
    suspend fun filterRoutesByCategoryAndDistance(
        jwt: String,
        userCoordinate: String,
        categories: Set<Int>
    ): List<RouteCardDto>

    // Поиск маршрутов по названию
    suspend fun getRoutesBySearchValue(
        jwt: String,
        userCoordinate: String,
        searchValue: String
    ): List<RouteCardDto>
}