package ru.hse.coursework.godaily.ui.components.superorganisms

import RouteCardSmall
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.model.Route
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RouteVerticalGrid(
    routes: List<Route>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        routes.chunked(2).forEach { routePair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                routePair.forEach { route ->
                    RouteCardSmall(
                        distance = "${route.length / 1000.0} км",
                        time = formatDuration(route.duration),
                        title = route.routeName,
                        imageRes = route.routePreview,
                        categories = listOf(
                            R.drawable.culture,
                            R.drawable.coffee,
                            R.drawable.metro,
                            R.drawable.nature
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 13.dp)
                    )
                }

                if (routePair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

//TODO: Убрать функцию, сделать нормальную работу с маршрутами
fun formatDuration(duration: LocalTime): String {
    return duration.format(DateTimeFormatter.ofPattern("mm")) + " минут"
}

@Preview
@Composable
fun PreviewRouteVerticalGrid() {
    val sampleRoutes = listOf(
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 45)
        ),
        Route(
            routeName = "Историческое Измайлово",
            length = 4600,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 50)
        ),
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 30)
        ),
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 45)
        ),
        Route(
            routeName = "Историческое Измайлово",
            length = 4600,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 50)
        ),
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 30)
        ),
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 45)
        ),
        Route(
            routeName = "Историческое Измайлово",
            length = 4600,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 50)
        ),
        Route(
            routeName = "Профсоюзная Зеленая тропа",
            length = 3000,
            routePreview = R.drawable.sample_route_image,
            duration = LocalTime.of(0, 30)
        )
    )
    RouteVerticalGrid(routes = sampleRoutes)
}
