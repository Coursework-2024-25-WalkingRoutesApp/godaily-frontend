package ru.hse.coursework.godaily.core.data.network

import com.yandex.mapkit.geometry.Point
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.data.model.RouteSessionDto
import ru.hse.coursework.godaily.core.data.model.UserDto
import java.time.LocalDateTime
import java.util.UUID

class FakeApiService : ApiService {

    val routeId = UUID.randomUUID()

    val userCheckpoints = mutableListOf<RouteSessionDto.UserCheckpoint>()

    val routeCoordinates = listOf(
        Point(55.6600, 37.5100),  // Старт
        Point(55.6780, 37.5250),  // Парк Горького
        Point(55.6900, 37.5450),  // Патриарший мост
        Point(55.7050, 37.5600),  // Храм Христа Спасителя
        Point(55.7200, 37.5800),  // Пушкинский музей
        Point(55.7350, 37.6000),  // Арбат
        Point(55.7450, 37.6100),  // Новый Арбат
        Point(55.7550, 37.6200),  // Московский международный деловой центр
        Point(55.7600, 37.6250),  // Башня Федерация
        Point(55.7700, 37.6300)   // Финиш
    ).mapIndexed { index, point ->
        val pointId = UUID.fromString(String.format("00000000-0000-0000-0000-%012d", index + 1))

        userCheckpoints.add(
            RouteSessionDto.UserCheckpoint(
                coordinateId = pointId,
                createdAt = LocalDateTime.now()
            )
        )

        val (title, description) = when (index) {
            0 -> "Стартовая точка" to "Начало маршрута. Подготовьтесь к прогулке."
            1 -> "Парк Горького" to "Центральный парк культуры и отдыха. Отличное место для начала прогулки."
            2 -> "Патриарший мост" to "Пешеходный мост через Москву-реку с прекрасным видом на Кремль."
            3 -> "Храм Христа Спасителя" to "Главный кафедральный собор Русской православной церкви."
            4 -> "Пушкинский музей" to "Государственный музей изобразительных искусств имени А.С. Пушкина."
            5 -> "Улица Арбат" to "Знаменитая пешеходная улица с сувенирными лавками и уличными артистами."
            6 -> "Новый Арбат" to "Современная деловая улица с небоскребами и торговыми центрами."
            7 -> "ММДЦ Москва-Сити" to "Московский международный деловой центр с высотными зданиями."
            8 -> "Башня Федерация" to "Один из самых высоких небоскребов в Европе."
            9 -> "Финишная точка" to "Конец маршрута. Вы успешно завершили прогулку!"
            else -> "Точка $index" to "Промежуточная точка маршрута."
        }

        RouteDto.RouteCoordinate(
            id = pointId,
            routeId = routeId,
            latitude = point.latitude,
            longitude = point.longitude,
            orderNumber = index + 1,
            title = title,
            description = description
        )
    }

    private val fakeUser =
        UserDto("yuulkht", "testuser@example.com", "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg")

    private val fakeRoutes = listOf(
        RouteCardDto(
            UUID.randomUUID(),
            "Историческая прогулка",
            5400.toDouble(),
            4000.toDouble(),
            "https://i.postimg.cc/nhTR2SBF/history.jpg",
            100.0,
            listOf(RouteDto.Category(UUID.randomUUID(), "Культурно-исторический"))
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Прогулка по набережной",
            3600.toDouble(),
            2500.toDouble(),
            "https://i.postimg.cc/QtqvLNW7/river.jpg",
            235.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Ночная экскурсия",
            7200.toDouble(),
            5000.toDouble(),
            "https://i.postimg.cc/g29sDBH5/night.jpg",
            500.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Поход к историческому музею",
            14400.toDouble(),
            12000.toDouble(),
            "https://i.postimg.cc/L5PJxvH4/museum.jpg",
            1200.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "У метро"),
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Кофейный тур по городу",
            4800.toDouble(),
            3000.toDouble(),
            "https://i.postimg.cc/DyV6nM4g/coffee.jpg",
            1500.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Культурно-исторический"),
                RouteDto.Category(UUID.randomUUID(), "У метро")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Путешествие по раменкам",
            9600.toDouble(),
            7500.toDouble(),
            "https://i.postimg.cc/SKK7g0Hy/ramenki.jpg",
            1684.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Живописные места для фотографий",
            7200.toDouble(),
            5500.toDouble(),
            "https://i.postimg.cc/59z57SKF/beautiful.jpg",
            2000.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "По следам известных фильмов",
            6000.toDouble(),
            4200.toDouble(),
            "https://i.postimg.cc/4d9c0XPT/filmtogo.jpg",
            3547.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Архитектурное наследие города",
            4800.toDouble(),
            3700.toDouble(),
            "https://i.postimg.cc/QNc5kxVQ/arci.jpg",
            5493.0,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                RouteDto.Category(UUID.randomUUID(), "Природный")
            )
        )
    )

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
    ): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun loginUser(email: String, password: String): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun checkVerification(): Response<Boolean> {
        return Response.success(true)
    }

    override suspend fun sendVerificationCode(email: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun checkVerificationCode(verificationCode: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getUserInfo(): Response<Any> {
        return Response.success(fakeUser)
    }

    override suspend fun saveUserPhoto(photo: MultipartBody.Part?): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun saveUserPhoto(photoUrl: String): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun saveUserEditedName(newUsername: String): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun addRoute(routeDto: RouteDto): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun getUserDrafts(

        latitude: Double,
        longitude: Double
    ): Response<List<RouteCardDto>> {
        return Response.success(
            listOf(
                RouteCardDto(
                    UUID.randomUUID(),
                    "Загадочные уголки города",
                    7200.toDouble(),
                    null,
                    "https://example.com/mystery-tour",
                    null,
                    listOf(RouteDto.Category(UUID.randomUUID(), "Культурно-исторический"))
                ),
                RouteCardDto(
                    UUID.randomUUID(),
                    null, // Название не указано
                    5400.toDouble(),
                    3500.toDouble(),
                    "https://example.com/unknown-route",
                    150.0,
                    listOf(
                        RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                        RouteDto.Category(UUID.randomUUID(), "У метро")
                    )
                ),
                RouteCardDto(
                    UUID.randomUUID(),
                    "Тайны подземного города",
                    null, // Длительность не указана
                    5000.toDouble(),
                    null, // Ссылка не указана
                    800.0,
                    listOf(
                        RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                        RouteDto.Category(UUID.randomUUID(), "Природный")
                    )
                )
            )
        )

    }

    override suspend fun getUserPublishedRoutes(

        latitude: Double,
        longitude: Double
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes.subList(7, 8))
    }

    override suspend fun getUserCompletedRoutes(

        latitude: Double,
        longitude: Double
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes.subList(2, 6))
    }

    override suspend fun getUserFavouriteRoutes(

        latitude: Double,
        longitude: Double
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes.take(2))
    }

    override suspend fun getUserUnfinishedRoutes(
        latitude: Double,
        longitude: Double
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes.takeLast(3))
    }

    override suspend fun addRouteToFavorites(

        routeId: UUID
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun removeRouteFromFavorites(

        routeId: UUID
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun getRouteDetails(
        routeId: UUID,
    ): Response<RoutePageDto> {
        return Response.success(
            RoutePageDto(
                id = UUID.randomUUID(),
                routeName = "Прогулка по набережной",
                description = "Этот маршрут идеально подойдёт для тех, кто хочет насладиться свежим воздухом и прекрасными видами реки. По пути можно встретить уютные кафе, зоны отдыха и живописные природные уголки. Отличное место для прогулки в одиночестве или в компании друзей.",
                duration = 3600.toDouble(),
                length = 2500.toDouble(),
                startPoint = "Центральная набережная",
                endPoint = "Пешеходный мост",
                routePreview = "https://i.postimg.cc/QtqvLNW7/river.jpg",
                isFavourite = false,
                routeCoordinate = routeCoordinates,
                categories = listOf(
                    RouteDto.Category(UUID.randomUUID(), "Кафе по пути"),
                    RouteDto.Category(UUID.randomUUID(), "Природный")
                )
            )
        )
    }

    override suspend fun getReviews(
        routeId: UUID,
    ): Response<ReviewDto> {
        return Response.success(
            ReviewDto(
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
        )
    }

    override suspend fun saveRouteSession(
        routeSessionDto: RouteSessionDto,
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun getRouteSession(

        routeId: UUID
    ): Response<RouteSessionDto> {
        return Response.success(
            RouteSessionDto(
                id = UUID.randomUUID(),
                routeId = routeId,
                isFinished = false,
                startedAt = LocalDateTime.now().minusHours(1),
                endedAt = null,
                userCheckpoint = emptyList()//userCheckpoints.dropLast(5)
            )
        )
    }

    override suspend fun saveReview(

        routeId: UUID,
        mark: Int,
        reviewText: String,
        createdAt: LocalDateTime
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun filterRoutesByCategoryAndDistance(

        latitude: Double,
        longitude: Double,
        categories: List<String>,
        radius: Long
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes)
    }

    override suspend fun getRoutesBySearchValue(
        searchValue: String,
        latitude: Double,
        longitude: Double,
        radius: Long
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes)
    }

    override suspend fun getRoutesForHomePage(
        latitude: Double,
        longitude: Double,
        radius: Long
    ): Response<List<RouteCardDto>> {
        return Response.success(fakeRoutes)
    }
}
