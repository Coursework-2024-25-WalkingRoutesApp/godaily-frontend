package ru.hse.coursework.godaily.core.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import ru.hse.coursework.godaily.core.data.model.UserDto
import java.time.LocalDateTime
import java.util.UUID

interface ApiService {

    //TODO
    // Регистрация нового пользователя
    suspend fun registerUser(
        email: String,
        password: String,
        username: String,
    ): Response<String> // Возвращает JWT

    //TODO
    // Вход в приложение
    suspend fun loginUser(email: String, password: String): Response<String> // Возвращает JWT

    // Получение информации о текущем пользователе
    //TODO
    suspend fun getUserInfo(jwt: String): UserDto

    //TODO
    // Сохранение информации о пользователе
    suspend fun saveUserPhoto(jwt: String, photo: ByteArray?): Response<String>

    //TODO
    // Сохранение информации о пользователе
    suspend fun saveUserEditedName(jwt: String, username: String): Response<String>

    // Сохранение маршрута
    @POST(ROUTE_BASE_PATH_URL + ADD_ROUTE_URL)
    suspend fun addRoute(
        @Body routeDto: RouteDto,
        @Query("userId") userId: UUID
    ): Response<String>

    // Получение черновиков пользователя
    @GET(ROUTE_BASE_PATH_URL + GET_DRAFTS_URL)
    suspend fun getUserDrafts(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<RouteCardDto>

    // Получение опубликованных маршрутов пользователя
    @GET(ROUTE_BASE_PATH_URL + GET_PUBLISHED_URL)
    suspend fun getUserPublishedRoutes(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<RouteCardDto>

    // Получение пройденных маршрутов пользователя
    @GET(SESSION_BASE_PATH_URL + GET_FINISHED_URL)
    suspend fun getUserCompletedRoutes(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<RouteCardDto>

    // Получение избранных маршрутов пользователя
    @GET(FAVORITE_BASE_PATH_URL + GET_FAVOURITES_URL)
    suspend fun getUserFavouriteRoutes(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<RouteCardDto>

    @GET(SESSION_BASE_PATH_URL + GET_UNFINISHED_URL)
    suspend fun getUserUnfinishedRoutes(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double

    ): List<RouteCardDto>

    // Добавление маршрута в избранное
    @POST(FAVORITE_BASE_PATH_URL + ADD_FAVOURITE_URL)
    suspend fun addRouteToFavorites(
        @Query("userId") userId: UUID,
        @Query("routeId") routeId: UUID
    ): Response<String>

    // Удаление маршрута из избранного
    @POST(FAVORITE_BASE_PATH_URL + DELETE_FAVOURITE_URL)
    suspend fun removeRouteFromFavorites(
        @Query("userId") userId: UUID,
        @Query("routeId") routeId: UUID
    ): Response<String>

    // Получение подробной информации о маршруте
    @GET(ROUTE_BASE_PATH_URL + GET_ROUTE_PAGE_URL)
    suspend fun getRouteDetails(
        @Query("routeId") routeId: UUID,
        @Query("userId") userId: UUID,
    ): RoutePageDto

    // Получение отзывов о маршруте
    @GET(REVIEW_BASE_PATH_URL + GET_REVIEWS_URL)
    suspend fun getReviews(
        @Query("routeId") routeId: UUID,
        @Query("userId") userId: UUID,
    ): ReviewDto

    // Создание сессии маршрута
    @POST(SESSION_BASE_PATH_URL + ADD_SESSION_URL)
    suspend fun saveRouteSession(
        @Body routeSessionDto: RouteSessionDto,
        @Query("userId") userId: UUID
    ): Response<String>

    // Получить сессию маршрута

    //TODO саше сделать и мне тоже :))
    suspend fun getRouteSession(jwt: String, routeId: UUID): RouteSessionDto

    // Сохранение отзыва о маршруте
    @POST(REVIEW_BASE_PATH_URL + ADD_REVIEW_URL)
    suspend fun saveReview(
        @Query("userId") userId: UUID,
        @Query("routeId") routeId: UUID,
        @Query("mark") mark: Int,
        @Query("reviewText") reviewText: String,
        @Query("createdText") createdAt: LocalDateTime
    ): Response<String>

    // Поиск маршрутов по названию
    @GET(ROUTE_BASE_PATH_URL + GET_ROUTE_BY_SEARCH_VALUE_URL)
    suspend fun getRoutesBySearchValue(
        @Query("searchValue") searchValue: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): List<RouteCardDto>

    // Фильтрация маршрутов по категории и сортировка по удаленности

    @GET(ROUTE_BASE_PATH_URL + GET_ROUTES_URL)
    suspend fun filterRoutesByCategoryAndDistance(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("categories") categories: List<String>
    ): List<RouteCardDto>

    @GET(ROUTE_BASE_PATH_URL + GET_ROUTES_URL)
    suspend fun getRoutesForHomePage(
        @Query("userId") userId: UUID,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<RouteCardDto>
}