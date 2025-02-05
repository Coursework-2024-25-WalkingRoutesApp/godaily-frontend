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
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import java.time.LocalTime
import java.util.UUID

@Composable
fun RouteVerticalGrid(
    routes: List<RouteCardDto>,
    onRouteClick: (RouteCardDto) -> Unit,
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
                        distance = "${route.distanceToUser / 1000.0} км",
                        time = formatDuration(route.duration),
                        title = route.routeName ?: "Название",
                        imageResUrl = route.routePreview ?: "",
                        categories = route.categories?.map { category ->
                            when (category.categoryName) {
                                "Culture" -> R.drawable.culture
                                "Coffee" -> R.drawable.coffee
                                "Metro" -> R.drawable.metro
                                "Nature" -> R.drawable.nature
                                //TODO: добавить unexpected icon
                                else -> R.drawable.end_point
                            }
                        } ?: emptyList(),
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
fun formatDuration(duration: LocalTime?): String {
    if (duration == null) {
        return "0 минут"
    }
    return "$duration минут"
}


@Preview
@Composable
fun PreviewRouteVerticalGrid() {
    val sampleRoutes = listOf(
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            LocalTime.ofSecondOfDay(120 * 60),
            5000,
            "City Center URL",
            3.5,
            listOf(RouteDto.Category(UUID.randomUUID(), "Culture"))
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            LocalTime.ofSecondOfDay(120 * 60),
            8000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            LocalTime.ofSecondOfDay(60 * 60),
            3000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            LocalTime.ofSecondOfDay(120 * 60),
            5000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            LocalTime.ofSecondOfDay(240 * 60),
            8000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Metro"),
                RouteDto.Category(UUID.randomUUID(), "Coffee")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            LocalTime.ofSecondOfDay(60 * 60),
            3000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Culture"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            LocalTime.ofSecondOfDay(120 * 60),
            5000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            LocalTime.ofSecondOfDay(240 * 60),
            8000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            LocalTime.ofSecondOfDay(60 * 60),
            3000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            LocalTime.ofSecondOfDay(120 * 60),
            5000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            LocalTime.ofSecondOfDay(240 * 60),
            8000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            LocalTime.ofSecondOfDay(60 * 60),
            3000,
            "City Center URL",
            3.5,
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        )
    )
    RouteVerticalGrid(routes = sampleRoutes, onRouteClick = {})
}
