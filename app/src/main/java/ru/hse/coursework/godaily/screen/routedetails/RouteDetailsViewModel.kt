package ru.hse.coursework.godaily.screen.routedetails

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.domain.routedetails.AddRouteToFavouritesUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteReviewsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RemoveRouteFromFavouritesUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.SaveReviewUseCase
import ru.hse.coursework.godaily.core.domain.routesession.FetchRouteSessionUseCase
import ru.hse.coursework.godaily.core.domain.routesession.SaveRouteSessionUseCase
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.core.domain.service.RouteYandexService
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase,
    private val addRouteToFavouritesUseCase: AddRouteToFavouritesUseCase,
    private val removeRouteFromFavouritesUseCase: RemoveRouteFromFavouritesUseCase,
    private val saveReviewUseCase: SaveReviewUseCase,
    private val fetchRouteReviewsUseCase: FetchRouteReviewsUseCase,
    private val fetchRouteSessionUseCase: FetchRouteSessionUseCase,
    private val saveRouteSessionUseCase: SaveRouteSessionUseCase,
    private val uuidService: UuidService,
    private val routeYandexService: RouteYandexService
) : ViewModel() {

    val route: MutableState<RoutePageDto> = mutableStateOf(
        RoutePageDto(
            id = UUID.randomUUID(),
            routeName = null,
            description = null,
            duration = null,
            length = null,
            startPoint = null,
            endPoint = null,
            routePreview = null,
            isFavourite = false,
            routeCoordinate = null,
            categories = null
        )
    )
    val averageMark: MutableState<Double> = mutableStateOf(5.0)
    val reviewsCount: MutableState<Int> = mutableStateOf(0)
    val isFavourite: MutableState<Boolean> = mutableStateOf(false)
    val userMark: MutableState<Int> = mutableStateOf(5)
    val reviewText: MutableState<String> = mutableStateOf("")
    var curUserReview: MutableState<ReviewDto.ReviewInfoDto>? = null

    val routeSessionId = mutableStateOf(UUID.randomUUID())
    val routePoints = mutableStateListOf<TitledPoint>()
    val passedPoints = mutableStateListOf<TitledPoint>()
    val distanceToNextPoint = mutableStateOf(0.toDouble())

    var isFinished = mutableStateOf(false)

    val showPauseDialog: MutableState<Boolean> = mutableStateOf(false)
    val isBackPressed: MutableState<Boolean> = mutableStateOf(false)
    val showFinishRouteDialog: MutableState<Boolean> = mutableStateOf(false)

    private val _reviews = MutableStateFlow<List<ReviewDto.ReviewInfoDto>>(emptyList())
    val reviews: StateFlow<List<ReviewDto.ReviewInfoDto>> = _reviews.asStateFlow()

    fun clear() {
        route.value = RoutePageDto(
            id = UUID.randomUUID(),
            routeName = null,
            description = null,
            duration = null,
            length = null,
            startPoint = null,
            endPoint = null,
            routePreview = null,
            isFavourite = false,
            routeCoordinate = null,
            categories = null
        )
        averageMark.value = 5.0
        reviewsCount.value = 0
        isFavourite.value = false
        userMark.value = 5
        reviewText.value = ""
        curUserReview = null

        routeSessionId.value = UUID.randomUUID()
        routePoints.clear()
        passedPoints.clear()
        distanceToNextPoint.value = 0.0
        isFinished.value = false

        showPauseDialog.value = false
        isBackPressed.value = false
        showFinishRouteDialog.value = false

        _reviews.value = emptyList()
    }


    fun updateRoute(routeValue: RoutePageDto) {
        route.value = routeValue
    }

    fun updateMark(markValue: Int) {
        userMark.value = markValue
    }

    fun updateReviewText(reviewTextValue: String) {
        reviewText.value = reviewTextValue
    }

    fun loadRateReviewDetails(routeId: String, mark: Int) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeDetails = fetchRouteDetailsUseCase.execute(routeIdUUID)
                updateRoute(routeDetails.route)
                updateMark(mark)
            }
        }
    }

    fun loadRouteReviews(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeReviews = fetchRouteReviewsUseCase.execute(routeIdUUID)
                curUserReview = if (routeReviews.curUserReview == null) {
                    null
                } else {
                    mutableStateOf(routeReviews.curUserReview)
                }

                _reviews.value = routeReviews.reviews
                averageMark.value = routeReviews.rating
                reviewsCount.value = routeReviews.reviewsCount
            }
        }
    }

    fun loadRouteDetails(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeDetails = fetchRouteDetailsUseCase.execute(routeIdUUID)
                route.value = routeDetails.route
                averageMark.value = routeDetails.mark
                reviewsCount.value = routeDetails.reviewsCount
                isFavourite.value = route.value.isFavourite
            }
        }
    }

    fun loadSessionPoints(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeSession = fetchRouteSessionUseCase.execute(routeIdUUID)

                routeSessionId.value = routeSession.id

                routePoints.clear()
                routePoints.addAll(routeSession.routePoints)

                passedPoints.clear()
                passedPoints.addAll(routeSession.passedRoutePoints)

                isFinished.value = routeSession.isFinished
            }
        }
    }

    suspend fun saveRouteSession(context: Context): Boolean {
        val result: Response<String> = saveRouteSessionUseCase.execute(
            id = routeSessionId.value,
            routeId = route.value.id,
            passedPoints = passedPoints,
            routePoints = routePoints
        )
        ToastManager(context).showToast(result.message())
        return result.isSuccessful
    }

    fun saveReview(context: Context) {
        viewModelScope.launch {

            val response = saveReviewUseCase.execute(
                route.value.id,
                reviewText.value,
                userMark.value
            )

            ToastManager(context).showToast(response.message())
        }
    }


    fun addRouteToFavourites() {
        viewModelScope.launch {
            addRouteToFavouritesUseCase.execute(route.value.id)
        }
    }

    fun removeRouteFromFavourites() {
        viewModelScope.launch {
            removeRouteFromFavouritesUseCase.execute(route.value.id)
        }
    }

    fun updateIsFavourite() {
        isFavourite.value = !isFavourite.value
    }

    fun updateDistanceToNextPoint(firstPoint: Point, secondPoint: Point) {
        viewModelScope.launch {
            distanceToNextPoint.value =
                routeYandexService.getDistancePointToPoint(firstPoint, secondPoint) ?: 0.toDouble()
        }
    }
}
