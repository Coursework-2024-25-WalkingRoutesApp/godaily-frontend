package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.RouteDto
import ru.hse.coursework.godaily.ui.components.molecules.Tag
import ru.hse.coursework.godaily.ui.components.quarks.FavouriteToggle
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouteTagsWithFavourite(
    categories: List<RouteDto.Category>,
    isFavorite: MutableState<Boolean>,
    onFavouriteToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val tag = when (category.categoryName) {
                    "Nature" -> "природный"
                    "Coffee" -> "кафе_по_пути"
                    "Metro" -> "у_метро"
                    "Culture" -> "культурный"
                    else -> ""
                }
                Tag(tag = tag)
            }
        }

        FavouriteToggle(
            isFavorite = isFavorite.value,
            onToggle = onFavouriteToggle,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RouteTagsWithFavouritePreview() {
    val isFavorite = remember {
        mutableStateOf(false)
    }
    RouteTagsWithFavourite(
        categories = listOf(
            RouteDto.Category(UUID.randomUUID(), "Nature"),
            RouteDto.Category(UUID.randomUUID(), "Coffee"),
            RouteDto.Category(UUID.randomUUID(), "Culture"),
            RouteDto.Category(UUID.randomUUID(), "Metro")
        ),
        isFavorite = isFavorite,
        onFavouriteToggle = { isFavorite.value = it }
    )
}

