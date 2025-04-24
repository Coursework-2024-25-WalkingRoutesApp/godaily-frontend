package ru.hse.coursework.godaily.core.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import java.time.LocalDateTime
import java.util.UUID

interface ApiService {

    @POST(USER_BASE_PATH_URL + REGISTER_URL)
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("username") username: String,
    ): Response<String>

    @GET(USER_BASE_PATH_URL + LOGIN_URL)
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<String>

    @GET(USER_BASE_PATH_URL + GET_USER_INFO_URL)
    suspend fun getUserInfo(): Response<Any>

    //TODO
    // Сохранение информации о пользователе
    @Multipart
    suspend fun saveUserPhoto(
        @Part photo: MultipartBody.Part?
    ): Response<String>

    // Сохранение имени пользователя
    @PUT(USER_BASE_PATH_URL + UPDATE_USERNAME_URL)
    suspend fun saveUserEditedName(
        @Query("newUsername") newUsername: String,
    ): Response<String>

    // Сохранение фото пользователя
    @PUT(USER_BASE_PATH_URL + UPDATE_USER_PHOTO_URL)
    suspend fun saveUserPhoto(
        @Query("photoUrl") photoUrl: String,
    ): Response<String>

    //Сохранение фото
    @Multipart
    @PUT(PHOTO_BASE_PATH_URL + UPLOAD_PHOTO_URL)
    suspend fun uploadPhoto(
        @Part photo: MultipartBody.Part,
        @Part("type") type: RequestBody,
        @Part("photoUrl") photoUrl: RequestBody?
    ): Response<String>

    // Сохранение маршрута
    @POST(ROUTE_BASE_PATH_URL + ADD_ROUTE_URL)
    suspend fun addRoute(
        @Body routeDto: RouteDto,
    ): Response<String>

    // Получение черновиков пользователя
    @GET(ROUTE_BASE_PATH_URL + GET_DRAFTS_URL)
    suspend fun getUserDrafts(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<RouteCardDto>>

    // Получение опубликованных маршрутов пользователя
    @GET(ROUTE_BASE_PATH_URL + GET_PUBLISHED_URL)
    suspend fun getUserPublishedRoutes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<RouteCardDto>>

    // Получение пройденных маршрутов пользователя
    @GET(SESSION_BASE_PATH_URL + GET_FINISHED_URL)
    suspend fun getUserCompletedRoutes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<RouteCardDto>>

    // Получение избранных маршрутов пользователя
    @GET(FAVORITE_BASE_PATH_URL + GET_FAVOURITES_URL)
    suspend fun getUserFavouriteRoutes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<RouteCardDto>>

    // Получение незавершенных маршрутов
    @GET(SESSION_BASE_PATH_URL + GET_UNFINISHED_URL)
    suspend fun getUserUnfinishedRoutes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double

    ): Response<List<RouteCardDto>>

    // Добавление маршрута в избранное
    @POST(FAVORITE_BASE_PATH_URL + ADD_FAVOURITE_URL)
    suspend fun addRouteToFavorites(
        @Query("routeId") routeId: UUID
    ): Response<String>

    // Удаление маршрута из избранного
    @DELETE(FAVORITE_BASE_PATH_URL + DELETE_FAVOURITE_URL)
    suspend fun removeRouteFromFavorites(
        @Query("routeId") routeId: UUID
    ): Response<String>

    // Получение подробной информации о маршруте
    @GET(ROUTE_BASE_PATH_URL + GET_ROUTE_PAGE_URL)
    suspend fun getRouteDetails(
        @Query("routeId") routeId: UUID,
    ): Response<RoutePageDto>

    // Получение отзывов о маршруте
    @GET(REVIEW_BASE_PATH_URL + GET_REVIEWS_URL)
    suspend fun getReviews(
        @Query("routeId") routeId: UUID,
    ): Response<ReviewDto>

    // Создание сессии маршрута
    @POST(SESSION_BASE_PATH_URL + ADD_SESSION_URL)
    suspend fun saveRouteSession(
        @Body routeSessionDto: RouteSessionDto,
    ): Response<String>

    // Получить сессию маршрута
    @GET(SESSION_BASE_PATH_URL + GET_SESSION_URL)
    suspend fun getRouteSession(
        @Query("routeId") routeId: UUID
    ): Response<RouteSessionDto>

    // Сохранение отзыва о маршруте
    @POST(REVIEW_BASE_PATH_URL + ADD_REVIEW_URL)
    suspend fun saveReview(
        @Query("routeId") routeId: UUID,
        @Query("mark") mark: Int,
        @Query("reviewText") reviewText: String,
        @Query("createdAt") createdAt: LocalDateTime
    ): Response<String>

    // Поиск маршрутов по названию
    @GET(ROUTE_BASE_PATH_URL + GET_ROUTE_BY_SEARCH_VALUE_URL)
    suspend fun getRoutesBySearchValue(
        @Query("searchValue") searchValue: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Long
    ): Response<List<RouteCardDto>>

    // Фильтрация маршрутов по категории и сортировка по удаленности

    @GET(ROUTE_BASE_PATH_URL + GET_ROUTES_URL)
    suspend fun filterRoutesByCategoryAndDistance(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("categories") categories: List<String>,
        @Query("radius") radius: Long
    ): Response<List<RouteCardDto>>

    @GET(ROUTE_BASE_PATH_URL + GET_ROUTES_URL)
    suspend fun getRoutesForHomePage(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Long
    ): Response<List<RouteCardDto>>
}