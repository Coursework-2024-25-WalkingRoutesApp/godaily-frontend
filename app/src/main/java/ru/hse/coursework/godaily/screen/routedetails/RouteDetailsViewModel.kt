package ru.hse.coursework.godaily.screen.routedetails

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.hse.coursework.godaily.core.data.model.ReviewDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.routedetails.AddRouteToFavouritesUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteReviewsUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RemoveRouteFromFavouritesUseCase
import ru.hse.coursework.godaily.core.domain.routedetails.RouteDetails
import ru.hse.coursework.godaily.core.domain.routedetails.RouteReviewsInfo
import ru.hse.coursework.godaily.core.domain.routedetails.SaveReviewUseCase
import ru.hse.coursework.godaily.core.domain.routesession.FetchRouteSessionUseCase
import ru.hse.coursework.godaily.core.domain.routesession.RoutePointsSession
import ru.hse.coursework.godaily.core.domain.routesession.SaveRouteSessionUseCase
import ru.hse.coursework.godaily.core.domain.routesession.TitledPoint
import ru.hse.coursework.godaily.core.domain.service.RouteYandexService
import ru.hse.coursework.godaily.core.domain.service.UuidService
import ru.hse.coursework.godaily.ui.errorsprocessing.ErrorHandler
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
    private val routeYandexService: RouteYandexService,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val route: MutableState<RoutePageDto> = mutableStateOf(
        RoutePageDto(
            id = null,
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
    var curUserReview: MutableState<ReviewDto.ReviewInfoDto?> = mutableStateOf(null)

    val routeSessionId: MutableState<UUID?> = mutableStateOf(null)
    val routePoints = mutableStateListOf<TitledPoint>()
    val passedPoints = mutableStateListOf<TitledPoint>()
    val distanceToNextPoint = mutableStateOf(0.toDouble())

    var isFinished = mutableStateOf(false)

    val showPauseDialog: MutableState<Boolean> = mutableStateOf(false)
    val isBackPressed: MutableState<Boolean> = mutableStateOf(false)
    val showFinishRouteDialog: MutableState<Boolean> = mutableStateOf(false)
    val showPassRouteAgainDialog: MutableState<Boolean> = mutableStateOf(false)

    val reviews = mutableStateListOf<ReviewDto.ReviewInfoDto>()

    fun clear() {
        route.value = RoutePageDto(
            id = null,
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
        curUserReview.value = null

        routeSessionId.value = null
        routePoints.clear()
        passedPoints.clear()
        distanceToNextPoint.value = 0.0
        isFinished.value = false

        showPauseDialog.value = false
        isBackPressed.value = false
        showFinishRouteDialog.value = false

        reviews.clear()
    }


    fun updateRoute(routeValue: RoutePageDto) {
        route.value = routeValue
    }

    fun updateMark(markValue: Int) {
        userMark.value = markValue
    }

    fun resetRouteSession() {
        passedPoints.clear()
        isFinished.value = false
    }

    fun loadRateReviewDetails(routeId: String, mark: Int) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                when (val routeDetailsResponse = fetchRouteDetailsUseCase.execute(routeIdUUID)) {
                    is ApiCallResult.Error -> errorHandler.handleError(routeDetailsResponse)
                    is ApiCallResult.Success -> {
                        if (routeDetailsResponse.data is RouteDetails) {
                            updateRoute(routeDetailsResponse.data.route)
                            updateMark(mark)
                        }

                    }
                }
            }
        }
    }

    fun loadRouteReviews(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                when (val routeReviewsResponse = fetchRouteReviewsUseCase.execute(routeIdUUID)) {
                    is ApiCallResult.Error -> errorHandler.handleError(routeReviewsResponse)
                    is ApiCallResult.Success -> {
                        if (routeReviewsResponse.data is RouteReviewsInfo) {
                            val routeReviews = routeReviewsResponse.data
                            curUserReview.value = if (routeReviews.curUserReview == null) {
                                null
                            } else {
                                routeReviews.curUserReview
                            }

                            reviews.clear()
                            reviews.addAll(routeReviews.reviews)

                            averageMark.value = routeReviews.rating
                            reviewsCount.value = routeReviews.reviewsCount
                        }
                    }
                }
            }
        }
    }

    fun loadRouteDetails(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeDetailsResponse = fetchRouteDetailsUseCase.execute(routeIdUUID)

                when (routeDetailsResponse) {
                    is ApiCallResult.Error -> errorHandler.handleError(routeDetailsResponse)
                    is ApiCallResult.Success -> {
                        if (routeDetailsResponse.data is RouteDetails) {
                            route.value = routeDetailsResponse.data.route
                            averageMark.value = routeDetailsResponse.data.mark
                            reviewsCount.value = routeDetailsResponse.data.reviewsCount
                            isFavourite.value = route.value.isFavourite

                            routePoints.clear()
                            routePoints.addAll(routeDetailsResponse.data.routePoints)
                        }
                    }
                }
            }
        }
    }

    fun loadSessionPoints(routeId: String) {
        val routeIdUUID = uuidService.getUUIDFromString(routeId)
        if (routeIdUUID != null) {
            viewModelScope.launch {
                val routeSessionResponse = fetchRouteSessionUseCase.execute(routeIdUUID)

                when (routeSessionResponse) {
                    is ApiCallResult.Error -> {
                        if (routeSessionResponse.code == 404) {
                            routeSessionId.value = null
                            isFinished.value = false
                        } else {
                            errorHandler.handleError(routeSessionResponse)
                        }
                    }

                    is ApiCallResult.Success -> {
                        if (routeSessionResponse.data is RoutePointsSession) {
                            routeSessionId.value = routeSessionResponse.data.id

                            routePoints.clear()
                            routePoints.addAll(routeSessionResponse.data.routePoints)

                            passedPoints.clear()
                            passedPoints.addAll(routeSessionResponse.data.passedRoutePoints)

                            isFinished.value = routeSessionResponse.data.isFinished
                        }
                    }
                }
            }
        }
    }

    fun saveRouteSession(context: Context): Boolean {
        return runBlocking {
            if (route.value.id != null) {
                val resultResponse = saveRouteSessionUseCase.execute(
                    id = routeSessionId.value,
                    routeId = route.value.id!!,
                    passedPoints = passedPoints,
                    routePoints = routePoints
                )

                when (resultResponse) {
                    is ApiCallResult.Error -> {
                        errorHandler.handleError(resultResponse)
                        false
                    }

                    is ApiCallResult.Success -> {
                        true
                    }
                }
            }
            false
        }
    }

    fun saveReview(context: Context) {
        viewModelScope.launch {
            if (route.value.id != null) {
                val resultResponse = saveReviewUseCase.execute(
                    route.value.id!!,
                    reviewText.value,
                    userMark.value
                )
                if (resultResponse is ApiCallResult.Error) {
                    errorHandler.handleError(resultResponse)
                }
            }
        }
    }


    fun addRouteToFavourites() {
        viewModelScope.launch {
            if (route.value.id != null) {
                addRouteToFavouritesUseCase.execute(route.value.id!!)
            }
        }
    }

    fun removeRouteFromFavourites() {
        viewModelScope.launch {
            if (route.value.id != null) {
                removeRouteFromFavouritesUseCase.execute(route.value.id!!)
            }
        }
    }

    fun updateIsFavourite() {
        isFavourite.value = !isFavourite.value
    }

    fun updateDistanceToNextPoint(firstPoint: Point?, secondPoint: Point?) {
        if (firstPoint == null || secondPoint == null) {
            distanceToNextPoint.value = 0.toDouble()
            return
        }
        viewModelScope.launch {
            distanceToNextPoint.value =
                routeYandexService.getDistancePointToPoint(firstPoint, secondPoint) ?: 0.toDouble()
        }
    }
}
