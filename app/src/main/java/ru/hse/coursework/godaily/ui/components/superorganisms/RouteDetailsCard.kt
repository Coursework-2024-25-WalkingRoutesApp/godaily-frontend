package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.core.data.model.ReviewDTO
import ru.hse.coursework.godaily.core.data.model.RoutePageDTO
import ru.hse.coursework.godaily.ui.components.organisms.RouteImageForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.RouteRatingForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.RouteTagsWithFavourite
import ru.hse.coursework.godaily.ui.components.organisms.RouteTitleDurationForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.ScrollableDescription
import java.time.LocalDateTime


@Composable
fun RouteDetailsCard(
    route: RoutePageDTO,
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
            categories = route.categories,
            isFavorite = route.isFavourite,
            onFavouriteToggle = onFavouriteToggle
        )

        RouteTitleDurationForDetailsCard(route = route)

        ScrollableDescription(description = route.description)

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
    val route = RoutePageDTO(
        id = "1",
        routeName = "Измайловский Кремль",
        description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы.",
        duration = 27,
        length = 2500,
        startPoint = "р-он. Измайлово",
        endPoint = "Измайловское шоссе, 73",
        routePreview = "https://via.placeholder.com/300",
        isFavourite = false,
        coordinates = emptyList(),
        categories = listOf(Category.CULTURE, Category.COFFEE, Category.METRO, Category.NATURE)
    )

    val reviews = listOf(
        ReviewDTO(
            userId = "1",
            username = "Игорь",
            photoURL = "https://via.placeholder.com/40",
            mark = 5,
            reviewText = "Отлично провел время, прогуливаясь по району Измайлово в Москве!",
            createdAt = LocalDateTime.now()
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
