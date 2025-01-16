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
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.ui.components.atoms.RouteDestinations
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameBig
import ru.hse.coursework.godaily.ui.components.molecules.RouteDurationInfo

@Composable
fun RouteTitleDurationForDetailsCard(
    route: RoutePageDTO
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            RouteNameBig(text = route.routeName)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                RouteDestinations(start = route.startPoint, end = route.endPoint)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))
            RouteDurationInfo(duration = "${route.duration} мин")

            // Расстояние
            RouteDurationInfo(duration = "${route.length} м")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RouteTitleDurationForDetailsCardPreview() {
    val route = RoutePageDTO(
        id = "1",
        routeName = "Измайловский Кремль",
        description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
        duration = 27,
        length = 2500,
        startPoint = "р-он. Измайлово",
        endPoint = "Измайловское шоссе, 73",
        routePreview = "https://via.placeholder.com/300",
        isFavourite = false,
        coordinates = emptyList(),
        categories = listOf(Category.Culture, Category.Coffee, Category.Metro, Category.Nature)
    )
    RouteTitleDurationForDetailsCard(
        route = route,
    )
}
