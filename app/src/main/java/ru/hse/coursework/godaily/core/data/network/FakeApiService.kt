package ru.hse.coursework.godaily.core.data.network

import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.ReviewsInfoDTO
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.core.data.model.RouteDTO
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.data.model.RouteSessionDTO
import ru.hse.coursework.godaily.core.data.model.UserDTO
import java.time.LocalDateTime

class FakeApiService : ApiService {

    private val fakeUser = UserDTO("Test User", "testuser@example.com", "fakePhotoUrl")

    private val fakeRoutes = listOf(
        RouteCardDTO(
            "1",
            "City Tour",
            120,
            5000,
            "City Center URL",
            listOf(Category.Coffee, Category.Nature)
        ),
        RouteCardDTO(
            "2",
            "Mountain Hike",
            240,
            8000,
            "City Center URL",
            listOf(Category.Metro, Category.Coffee)
        ),
        RouteCardDTO(
            "3",
            "Beach Walk",
            60,
            3000,
            "City Center URL",
            listOf(Category.Culture, Category.Metro)
        )
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

    override suspend fun getRouteDetails(jwt: String, routeId: String): RoutePageDTO {
        return RoutePageDTO(
            id = "1",
            routeName = "Измайловский Кремль",
            description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
            duration = 27,
            length = 2500,
            startPoint = "р-он. Измайлово",
            endPoint = "Измайловское шоссе, 73",
            routePreview = "https://via.placeholder.com/300",
            isFavourite = false,
            coordinates = emptyList(),
            categories = listOf(Category.Culture, Category.Coffee, Category.Metro, Category.Nature)
        )
    }

    override suspend fun getReviewsInfo(jwt: String, routeId: String): ReviewsInfoDTO {
        return ReviewsInfoDTO(
            userId = "90",
            listOf(
                ReviewDTO(
                    userId = "0",
                    username = "Иван Иванов",
                    photoURL = "https://example.com/photo1.jpg",
                    mark = 5,
                    reviewText = "Этот маршрут превзошел все мои ожидания! Я давно искал что-то подобное — живописные виды, разнообразные ландшафты и интересные достопримечательности. Он идеально подходит для тех, кто хочет увидеть как можно больше за короткое время. Очень понравилось, что на пути были предусмотрены места для отдыха с видом на красивые пейзажи. Обязательно буду рекомендовать своим друзьям, а сам вернусь еще не раз!",
                    createdAt = LocalDateTime.of(2023, 12, 25, 14, 30, 0, 0)
                ),
                ReviewDTO(
                    userId = "2",
                    username = "Мария Петрова",
                    photoURL = "https://example.com/photo2.jpg",
                    mark = 4,
                    reviewText = "Маршрут хороший, но не без недостатков. В целом, он довольно интересный, с красивыми видами и множеством остановок, где можно сделать фото. Однако были некоторые участки, где я чувствовала себя не совсем комфортно: узкие тропинки и недостаточно указателей на некоторых поворотах. Я думаю, что это можно улучшить. В целом, это было приятное путешествие, и я бы снова попробовала пройти этот маршрут.",
                    createdAt = LocalDateTime.of(2024, 1, 10, 9, 15, 0, 0)
                ),
                ReviewDTO(
                    userId = "3",
                    username = "Алексей Браун",
                    photoURL = "https://example.com/photo3.jpg",
                    mark = 3,
                    reviewText = "Маршрут в целом нормальный, но мне показалось, что он слишком простой и не особо захватывающий. Местами не хватает интересных моментов. Для новичков, наверное, будет хорошо, но для более опытных туристов я бы посоветовал выбрать что-то более сложное и увлекательное. В целом, прогулка прошла без особых проблем, но я ожидал большего.",
                    createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
                ),
                ReviewDTO(
                    userId = "1",
                    username = "Котеночек",
                    photoURL = "https://amicus-vet.ru/images/statii/a582d6cs-960.jpg",
                    mark = 5,
                    reviewText = "Ничего не знаю, у меня лапки",
                    createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
                )
            )
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

    override suspend fun getRoutesSortedByDistance(
        jwt: String,
        userCoordinate: String
    ): List<RouteCardDTO> {
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
