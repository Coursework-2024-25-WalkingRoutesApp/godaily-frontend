package ru.hse.coursework.godaily.core.data.network

import com.yandex.mapkit.geometry.Point
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.ReviewPublishDto
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import ru.hse.coursework.godaily.core.data.model.UserCoordinateDto
import ru.hse.coursework.godaily.core.data.model.UserDto
import java.time.LocalDateTime
import java.util.UUID

class FakeApiService : ApiService {

    val routeId = UUID.randomUUID()

    val userCheckpoints = mutableListOf<RouteSessionDto.UserCheckpoint>()

    val routeCoordinates = listOf(
        Point(55.6600, 37.5100),
        Point(55.6780, 37.5250),
        Point(55.6900, 37.5450),
        Point(55.7050, 37.5600),
        Point(55.7200, 37.5800),
        Point(55.7350, 37.6000),
        Point(55.7450, 37.6100),
        Point(55.7550, 37.6200),
        Point(55.7600, 37.6250),
        Point(55.7700, 37.6300)
    ).mapIndexed { index, point ->
        val pointId = UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index + 1))

        userCheckpoints.add(
            RouteSessionDto.UserCheckpoint(
                coordinateId = pointId,
                createdAt = LocalDateTime.now()
            )
        )

        RouteDto.RouteCoordinate(
            id = pointId,
            routeId = routeId,
            latitude = point.latitude,
            longitude = point.longitude,
            orderNumber = index + 1,
            title = "Точка $index",
            description = "Описание точки"
        )
    }

    private val fakeUser = UserDto("Test User", "testuser@example.com", "fakePhotoUrl")

    private val fakeRoutes = listOf(
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            7200.toDouble(),
            5000.toDouble(),
            "City Center URL",
            3.5,
            listOf(RouteDto.Category(UUID.randomUUID(), "Culture"))
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            7200.toDouble(),
            8000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            7200.toDouble(),
            5000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            14400.toDouble(),
            8000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Metro"),
                RouteDto.Category(UUID.randomUUID(), "Coffee")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Culture"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
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

    override suspend fun getUserInfo(jwt: String): UserDto {
        return fakeUser
    }

    override suspend fun addRoute(jwt: String, route: RouteDto): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun getUserDrafts(
        jwt: String,
        userLocation: UserCoordinateDto
    ): List<RouteCardDto> {
        return fakeRoutes.take(3)
    }

    override suspend fun getUserPublishedRoutes(
        jwt: String,
        userLocation: UserCoordinateDto
    ): List<RouteCardDto> {
        return fakeRoutes
    }

    override suspend fun getUserCompletedRoutes(
        jwt: String,
        userLocation: UserCoordinateDto
    ): List<RouteCardDto> {
        return fakeRoutes
    }

    override suspend fun getUserFavouriteRoutes(
        jwt: String,
        userLocation: UserCoordinateDto
    ): List<RouteCardDto> {
        return fakeRoutes.take(2)
    }

    override suspend fun getUserUnfinishedRoutes(
        jwt: String,
        userLocation: UserCoordinateDto
    ): List<RouteCardDto> {
        return fakeRoutes.take(3)
    }

    override suspend fun addRouteToFavorites(jwt: String, routeId: UUID): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun removeRouteFromFavorites(jwt: String, routeId: UUID): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun deleteRoute(jwt: String, routeId: UUID): Boolean {
        return true
    }

    override suspend fun getRouteDetails(jwt: String, routeId: UUID): RoutePageDto {
        return RoutePageDto(
            id = UUID.randomUUID(),
            routeName = "Измайловский Кремль",
            description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
            duration = 7200.toDouble(),
            length = 2500.toDouble(),
            startPoint = "р-он. Измайлово",
            endPoint = "Измайловское шоссе, 73",
            routePreview = "https://via.placeholder.com/300",
            isFavourite = false,
            routeCoordinate = routeCoordinates,
            categories = listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Culture"),
                RouteDto.Category(UUID.randomUUID(), "Nature"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        )
    }

    override suspend fun getReviews(jwt: String, routeId: UUID): ReviewDto {
        return ReviewDto(
            curUserId = UUID.randomUUID(),
            listOf(
                ReviewDto.ReviewInfoDto(
                    userId = UUID.randomUUID(),
                    userName = "Иван Иванов",
                    userPhotoUrl = "https://example.com/photo1.jpg",
                    rating = 5,
                    reviewText = "Этот маршрут превзошел все мои ожидания! Я давно искал что-то подобное — живописные виды, разнообразные ландшафты и интересные достопримечательности. Он идеально подходит для тех, кто хочет увидеть как можно больше за короткое время. Очень понравилось, что на пути были предусмотрены места для отдыха с видом на красивые пейзажи. Обязательно буду рекомендовать своим друзьям, а сам вернусь еще не раз!",
                    createdAt = LocalDateTime.of(2023, 12, 25, 14, 30, 0, 0)
                ),
                ReviewDto.ReviewInfoDto(
                    userId = UUID.randomUUID(),
                    userName = "Мария Петрова",
                    userPhotoUrl = "https://example.com/photo2.jpg",
                    rating = 4,
                    reviewText = "Маршрут хороший, но не без недостатков. В целом, он довольно интересный, с красивыми видами и множеством остановок, где можно сделать фото. Однако были некоторые участки, где я чувствовала себя не совсем комфортно: узкие тропинки и недостаточно указателей на некоторых поворотах. Я думаю, что это можно улучшить. В целом, это было приятное путешествие, и я бы снова попробовала пройти этот маршрут.",
                    createdAt = LocalDateTime.of(2024, 1, 10, 9, 15, 0, 0)
                ),
                ReviewDto.ReviewInfoDto(
                    userId = UUID.randomUUID(),
                    userName = "Алексей Браун",
                    userPhotoUrl = "https://example.com/photo3.jpg",
                    rating = 3,
                    reviewText = "Маршрут в целом нормальный, но мне показалось, что он слишком простой и не особо захватывающий. Местами не хватает интересных моментов. Для новичков, наверное, будет хорошо, но для более опытных туристов я бы посоветовал выбрать что-то более сложное и увлекательное. В целом, прогулка прошла без особых проблем, но я ожидал большего.",
                    createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
                ),
                ReviewDto.ReviewInfoDto(
                    userId = UUID.randomUUID(),
                    userName = "Котеночек",
                    userPhotoUrl = "https://amicus-vet.ru/images/statii/a582d6cs-960.jpg",
                    rating = 5,
                    reviewText = "Ничего не знаю, у меня лапки",
                    createdAt = LocalDateTime.of(2024, 1, 12, 18, 45, 0, 0)
                )
            )
        )
    }

    override suspend fun createRouteSession(jwt: String, routeSession: RouteSessionDto): Boolean {
        return true
    }

    override suspend fun updateRouteSession(jwt: String, routeSession: RouteSessionDto): Boolean {
        return true
    }

    override suspend fun getRouteSession(jwt: String, routeId: UUID): RouteSessionDto {
        return RouteSessionDto(
            id = UUID.randomUUID(),
            routeId = routeId,
            isFinished = false,
            startedAt = LocalDateTime.now().minusHours(1),
            endedAt = null,
            userCheckpoint = emptyList()//userCheckpoints.dropLast(5)
        )
    }

    override suspend fun saveReview(jwt: String, review: ReviewPublishDto): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun filterRoutesByCategoryAndDistance(
        jwt: String,
        userCoordinate: UserCoordinateDto,
        categories: Set<Int>
    ): List<RouteCardDto> {
        return fakeRoutes
    }

    override suspend fun getRoutesBySearchValue(
        jwt: String,
        userCoordinate: UserCoordinateDto,
        searchValue: String
    ): List<RouteCardDto> {
        return fakeRoutes
    }
}
