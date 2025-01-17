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
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO

@Composable
fun RouteVerticalGrid(
    routes: List<RouteCardDTO>,
    onRouteClick: (RouteCardDTO) -> Unit,
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
                        imageResUrl = route.routePreview,
                        categories = route.categories.map { category ->
                            when (category) {
                                Category.Culture -> R.drawable.culture
                                Category.Coffee -> R.drawable.coffee
                                Category.Metro -> R.drawable.metro
                                Category.Nature -> R.drawable.nature
                            }
                        },
                        onCardClick = { onRouteClick(route) },
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
fun formatDuration(duration: Int): String {
    return "$duration минут"
}


@Preview
@Composable
fun PreviewRouteVerticalGrid() {
    val sampleRoutes = listOf(
        RouteCardDTO(
            "1",
            "Исторический центр",
            120,
            5000,
            "City Center URL",
            listOf(Category.Coffee, Category.Nature)
        ),
        RouteCardDTO(
            "2",
            "Природная тропа",
            240,
            8000,
            "City Center URL",
            listOf(Category.Metro, Category.Coffee)
        ),
        RouteCardDTO(
            "3",
            "Красивое Измайлово",
            60,
            3000,
            "City Center URL",
            listOf(Category.Culture, Category.Metro)
        )
    )
    RouteVerticalGrid(routes = sampleRoutes, onRouteClick = {})
}
