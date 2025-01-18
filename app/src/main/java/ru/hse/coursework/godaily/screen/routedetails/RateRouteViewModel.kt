package ru.hse.coursework.godaily.screen.routedetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.core.domain.routedetails.FetchRouteDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class RateRouteViewModel @Inject constructor(
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase
) : ViewModel() {
    val route: MutableState<RoutePageDTO> = mutableStateOf(RoutePageDTO(
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        "",
        false,
        listOf(),
        listOf()
    ))
    val mark: MutableState<Int> = mutableStateOf(5)
    val reviewText: MutableState<String> = mutableStateOf("")

    fun updateRoute(routeValue: RoutePageDTO ) {
        route.value = routeValue
    }

    fun updateMark(markValue: Int ) {
        mark.value = markValue
    }

    fun updateReviewText(reviewTextValue: String ) {
        reviewText.value = reviewTextValue
    }



    //TODO: баг с звездочками на экране - все белые, а должно быть mark закрашенных
    fun loadRateReviewDetails(routeId: String, mark: Int) {
        viewModelScope.launch {
            val routeDetails = fetchRouteDetailsUseCase.execute(routeId)
            updateRoute(routeDetails.route)
            updateMark(mark)
        }
    }
}
