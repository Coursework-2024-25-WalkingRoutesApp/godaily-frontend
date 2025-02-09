package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yandex.mapkit.geometry.Point
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.organisms.YandexMapCreateRouteView

@Composable
fun CreateRouteView(
    routePoints: MutableList<Point>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {

        if (routePoints.isEmpty()) {
            VariableMedium(
                text = "Выберите старт маршрута",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            VariableMedium(
                text = "Выберите следующую точку",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        YandexMapCreateRouteView(
            routePoints = routePoints,
            modifier = Modifier.fillMaxSize()
        )
    }
}
