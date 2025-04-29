package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.ui.components.atoms.RouteDestinations
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameBig
import ru.hse.coursework.godaily.ui.components.molecules.RouteDurationInfo
import ru.hse.coursework.godaily.ui.components.superorganisms.formatDistance
import ru.hse.coursework.godaily.ui.components.superorganisms.formatDuration
import java.util.UUID

@Composable
fun RouteTitleDurationForDetailsCard(
    route: RoutePageDto,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RouteNameBig(text = route.routeName ?: "")

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                RouteDestinations(start = route.startPoint ?: "", end = route.endPoint ?: "")
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))
            RouteDurationInfo(duration = formatDuration(route.duration))

            // Расстояние
            RouteDurationInfo(duration = formatDistance(route.length))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RouteTitleDurationForDetailsCardPreview() {
    val route = RoutePageDto(
        id = UUID.randomUUID(),
        routeName = "Измайловский Кремль",
        description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
        duration = 7200.toDouble(),
        length = 2500.toDouble(),
        startPoint = "р-он. Измайлово",
        endPoint = "Измайловское шоссе, 73",
        routePreview = "https://via.placeholder.com/300",
        isFavourite = false,
        routeCoordinate = emptyList(),
        categories = listOf(
            RouteDto.Category(UUID.randomUUID(), "Coffee"),
            RouteDto.Category(UUID.randomUUID(), "Culture"),
            RouteDto.Category(UUID.randomUUID(), "Nature"),
            RouteDto.Category(UUID.randomUUID(), "Metro")
        )
    )
    RouteTitleDurationForDetailsCard(
        route = route,
    )
}
