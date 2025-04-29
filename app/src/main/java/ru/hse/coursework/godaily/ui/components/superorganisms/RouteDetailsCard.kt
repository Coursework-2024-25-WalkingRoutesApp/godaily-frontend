package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.ui.components.organisms.RouteImageForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.RouteTagsWithFavourite
import ru.hse.coursework.godaily.ui.components.organisms.RouteTitleDurationForDetailsCard
import ru.hse.coursework.godaily.ui.components.organisms.ScrollableDescription
import java.util.UUID


@Composable
fun RouteDetailsCard(
    imageLoader: ImageLoader,
    route: RoutePageDto,
    isFavourite: MutableState<Boolean>,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
    onFavouriteToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        RouteImageForDetailsCard(
            route = route,
            onBackClick = onBackClick,
            onMapClick = onMapClick,
            imageLoader = imageLoader
        )

        Spacer(modifier = Modifier.height(16.dp))

        RouteTagsWithFavourite(
            categories = route.categories ?: emptyList(),
            isFavorite = isFavourite,
            onFavouriteToggle = onFavouriteToggle
        )

        RouteTitleDurationForDetailsCard(route = route)

        Box(
            modifier = Modifier.weight(1f)
        ) {
            ScrollableDescription(
                description = route.description ?: "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RouteDetailsCardPreview() {
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

    RouteDetailsCard(
        route = route,
        isFavourite = mutableStateOf(false),
        onBackClick = {},
        onMapClick = {},
        onFavouriteToggle = {},
        imageLoader = ImageLoader(LocalContext.current)
    )
}
