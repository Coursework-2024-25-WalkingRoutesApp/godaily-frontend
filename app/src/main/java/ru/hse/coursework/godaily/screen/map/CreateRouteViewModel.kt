package ru.hse.coursework.godaily.screen.map

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRouteViewModel @Inject constructor(
    //private val saveRouteUseCase: FetchRouteDetailsUseCase
) : ViewModel() {

    val routeTitle: MutableState<String> = mutableStateOf("")
    val routeDescription: MutableState<String> = mutableStateOf("")
    val startPoint: MutableState<String> = mutableStateOf("")
    val endPoint: MutableState<String> = mutableStateOf("")
    val routePoints = mutableStateListOf<Point>()
    val chosenCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)

    fun updateRouteTitle(routeTitleValue: String) {
        routeTitle.value = routeTitleValue
    }

    fun saveRouteToDrafts() {}

    fun publishRoute() {}
}
