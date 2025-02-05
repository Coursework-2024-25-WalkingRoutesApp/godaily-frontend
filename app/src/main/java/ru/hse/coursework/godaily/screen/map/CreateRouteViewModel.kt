package ru.hse.coursework.godaily.screen.map

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.coursework.godaily.core.domain.routes.SaveRouteUseCase
import javax.inject.Inject

@HiltViewModel
class CreateRouteViewModel @Inject constructor(
    private val saveRouteUseCase: SaveRouteUseCase
) : ViewModel() {

    val routeTitle: MutableState<String> = mutableStateOf("")
    val routeDescription: MutableState<String> = mutableStateOf("")
    val startPoint: MutableState<String> = mutableStateOf("")
    val endPoint: MutableState<String> = mutableStateOf("")
    val routePoints = mutableStateListOf<Point>()
    val chosenCategories: MutableState<Set<Int>> = mutableStateOf(setOf())
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val showPublishWarningDialog: MutableState<Boolean> = mutableStateOf(false)
    val showNewPublishDialog: MutableState<Boolean> = mutableStateOf(false)

    fun updateRouteTitle(routeTitleValue: String) {
        routeTitle.value = routeTitleValue
    }

    fun saveRouteToDrafts() {
        //TODO
    }

    fun publishRoute() {
        //TODO
    }
}
