package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.core.data.model.RoutePageDto
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.Map
import java.util.UUID

@Composable
fun RouteImageForDetailsCard(
    route: RoutePageDto,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(
                model = route.routePreview,
                error = painterResource(R.drawable.route_to_continue_default)
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Back(onClick = onBackClick)
            Map(onClick = onMapClick)
        }
    }
}

@Preview
@Composable
fun RouteImageForDetailsCardPreview() {
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
    RouteImageForDetailsCard(
        route = route,
        onBackClick = {},
        onMapClick = {},
    )
}