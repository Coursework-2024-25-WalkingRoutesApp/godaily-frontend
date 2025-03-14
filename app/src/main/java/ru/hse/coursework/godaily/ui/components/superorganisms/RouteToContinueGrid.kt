package ru.hse.coursework.godaily.ui.components.superorganisms

import RouteCardToContinue
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.core.data.model.RouteCardDto
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import java.util.UUID

@Composable
fun RouteToContinueGrid(
    routes: List<RouteCardDto>,
    modifier: Modifier = Modifier,
    onRouteClick: (RouteCardDto) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(165.dp)
    ) {
        VariableMedium(
            text = "Продолжи маршрут",
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            routes.forEach { route ->
                RouteCardToContinue(
                    distance = formatDistance(route.distanceToUser),
                    title = route.routeName ?: "Название",
                    imageResUrl = route.routePreview ?: "",
                    modifier = Modifier.padding(end = 8.dp),
                    onCardClick = { onRouteClick(route) }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewRouteToContinueGrid() {
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
            5000.toDouble(),
            "City Center URL",
            3545.toDouble(),
            listOf(
                RouteDto.Category(UUID.randomUUID(), "Coffee"),
                RouteDto.Category(UUID.randomUUID(), "Nature")
            )
        )
    )
    RouteToContinueGrid(
        routes = sampleRoutes,
        onRouteClick = {}
    )
}
