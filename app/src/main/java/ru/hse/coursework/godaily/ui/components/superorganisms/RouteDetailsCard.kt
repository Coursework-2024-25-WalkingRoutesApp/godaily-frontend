package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.ui.components.organisms.RouteImageForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.RouteRatingForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.RouteTagsWithFavourite
import ru.hse.coursework.godaily.ui.components.organisms.RouteTitleDurationForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.ScrollableDescription
import java.time.LocalTime
import java.util.UUID


@Composable
fun RouteDetailsCard(
    route: RoutePageDto,
    mark: Double,
    reviewsCount: Int,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
    onFavouriteToggle: (Boolean) -> Unit,
    onReviewsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RouteImageForDetailsCard(
            route = route,
            onBackClick = onBackClick,
            onMapClick = onMapClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        RouteTagsWithFavourite(
            categories = route.categories ?: emptyList(),
            isFavorite = route.isFavourite ?: false,
            onFavouriteToggle = onFavouriteToggle
        )

        RouteTitleDurationForDetailsCard(route = route)

        ScrollableDescription(description = route.description ?: "")

        RouteRatingForDetailsCard(
            rating = mark,
            reviewsCount = reviewsCount,
            onReviewsClick = onReviewsClick
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RouteDetailsCardPreview() {
    val route = RoutePageDto(
        id = UUID.randomUUID(),
        routeName = "Измайловский Кремль",
        description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
        duration = LocalTime.ofSecondOfDay(120 * 60),
        length = 2500,
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

    RouteDetailsCard(
        route = route,
        mark = 4.7,
        reviewsCount = 79,
        onBackClick = {},
        onMapClick = {},
        onFavouriteToggle = {},
        onReviewsClick = {}
    )
}
