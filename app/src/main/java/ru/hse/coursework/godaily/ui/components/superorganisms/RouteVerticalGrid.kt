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
import ru.hse.coursework.godaily.core.data.model.RouteDTO

@Composable
fun RouteVerticalGrid(
    routes: List<RouteDTO>,
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
fun formatDuration(duration: Int): String {
    return "$duration минут"
}

@Preview
@Composable
fun PreviewRouteVerticalGrid() {
    val sampleRoutes = listOf(
        RouteDTO(
            id = "101",
            userId = "1",
            routeName = "Mock Draft Route",
            description = "This is a draft route",
            duration = 60,
            length = 5000,
            startPoint = "Start Point",
            endPoint = "End Point",
            routePreview = "https://example.com/route_preview.jpg",
            isDraft = true,
            lastModifiedAt = "2025-01-12",
            createdAt = "2025-01-01"
        ),
        RouteDTO(
            id = "101",
            userId = "2",
            routeName = "Mock Draft Route",
            description = "This is a draft route",
            duration = 60,
            length = 5000,
            startPoint = "Start Point",
            endPoint = "End Point",
            routePreview = "https://example.com/route_preview.jpg",
            isDraft = true,
            lastModifiedAt = "2025-01-12",
            createdAt = "2025-01-01"
        ),
        RouteDTO(
            id = "101",
            userId = "3",
            routeName = "Mock Draft Route",
            description = "This is a draft route",
            duration = 60,
            length = 5000,
            startPoint = "Start Point",
            endPoint = "End Point",
            routePreview = "https://example.com/route_preview.jpg",
            isDraft = true,
            lastModifiedAt = "2025-01-12",
            createdAt = "2025-01-01"
        ),
        RouteDTO(
            id = "101",
            userId = "4",
            routeName = "Mock Draft Route",
            description = "This is a draft route",
            duration = 60,
            length = 5000,
            startPoint = "Start Point",
            endPoint = "End Point",
            routePreview = "https://example.com/route_preview.jpg",
            isDraft = true,
            lastModifiedAt = "2025-01-12",
            createdAt = "2025-01-01"
        ),
    )
    RouteVerticalGrid(routes = sampleRoutes)
}
