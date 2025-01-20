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
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.RouteCardDTO
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium

@Composable
fun RouteToContinueGrid(
    routes: List<RouteCardDTO>,
    modifier: Modifier = Modifier
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
                    distance = "${route.length / 1000.0} км",
                    title = route.routeName,
                    imageResUrl = route.routePreview,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewRouteToContinueGrid() {
    val sampleRoutes = listOf(
        RouteCardDTO(
            "1",
            "Исторический центр",
            120,
            5000,
            "City Center URL",
            listOf(Category.COFFEE, Category.NATURE)
        ),
        RouteCardDTO(
            "2",
            "Природная тропа",
            240,
            8000,
            "City Center URL",
            listOf(Category.METRO, Category.COFFEE)
        ),
        RouteCardDTO(
            "3",
            "Красивое Измайлово",
            60,
            3000,
            "City Center URL",
            listOf(Category.CULTURE, Category.METRO)
        )
    )
    RouteToContinueGrid(routes = sampleRoutes)
}
