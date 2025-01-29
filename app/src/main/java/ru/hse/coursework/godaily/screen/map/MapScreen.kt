package ru.hse.coursework.godaily.screen.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import ru.hse.coursework.godaily.ui.components.atoms.HeaderBig
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.organisms.YandexMapCreateRouteView

@Composable
fun MapScreen(
    navController: NavController
) {
    DisposableEffect(Unit) {
        MapKitFactory.getInstance().onStart()

        onDispose {
            MapKitFactory.getInstance().onStop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Back(
                onClick = { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            HeaderBig(text = "Создание маршрута")
        }

        YandexMapCreateRouteView(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 50.dp, bottom = 100.dp),
            routePoints = mutableListOf()
        )
    }
}
