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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import java.util.UUID

@Composable
fun RouteVerticalGrid(
    imageLoader: ImageLoader,
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
                        distance = formatDistance(route.distanceToUser),
                        time = formatDuration(route.duration),
                        title = route.routeName ?: "Название",
                        imageResUrl = route.routePreview ?: "",
                        categories = route.categories?.mapNotNull { category ->
                            when (category.categoryName) {
                                "Культурно-исторический" -> R.drawable.culture
                                "Кафе по пути" -> R.drawable.coffee
                                "У метро" -> R.drawable.metro
                                "Природный" -> R.drawable.nature
                                else -> null
                            }
                        } ?: emptyList(),
                        onCardClick = { onRouteClick(route) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 13.dp),
                        imageLoader = imageLoader
                    )
                }

                if (routePair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

fun formatDuration(duration: Double?): String {
    if (duration == null) {
        return "0 минут"
    }
    val minutes = (duration / 60).toInt()
    return when {
        minutes == 0 -> "менее минуты"
        minutes % 10 == 1 && minutes % 100 != 11 -> "$minutes минута"
        minutes % 10 in 2..4 && (minutes % 100 !in 12..14) -> "$minutes минуты"
        else -> "$minutes минут"
    }
}

fun formatDistance(distance: Double?): String {
    if (distance == null) {
        return "0 м"
    }
    return when {
        distance < 1000 -> "${distance.toInt()} м"
        else -> String.format("%.1f км", distance / 1000)
    }
}


@Preview
@Composable
fun PreviewRouteVerticalGrid() {
    val sampleRoutes = listOf(
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            7200.toDouble(),
            5000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(RouteDto.Category(UUID.randomUUID(), "Culture"))
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            7200.toDouble(),
            8000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            100.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            7200.toDouble(),
            5000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            14400.toDouble(),
            8000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Metro"),
                RouteDto.Category(UUID.randomUUID(), "Coffee")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Culture"),
                RouteDto.Category(UUID.randomUUID(), "Metro")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "City Tour",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Mountain Hike",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        ),
        RouteCardDto(
            UUID.randomUUID(),
            "Beach Walk",
            3600.toDouble(),
            3000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        )
    )
    RouteVerticalGrid(
        routes = sampleRoutes,
        onRouteClick = {},
        imageLoader = ImageLoader(LocalContext.current)
    )
}
