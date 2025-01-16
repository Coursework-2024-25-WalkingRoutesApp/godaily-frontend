package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.core.data.model.Category
import ru.hse.coursework.godaily.ui.components.molecules.Tag
import ru.hse.coursework.godaily.ui.components.quarks.FavouriteToggle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouteTagsWithFavourite(
    categories: List<Category>,
    isFavorite: Boolean,
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
                val tag = when (category) {
                    Category.Nature -> "природный"
                    Category.Coffee -> "кафе_по_пути"
                    Category.Metro -> "у_метро"
                    Category.Culture -> "культурный"
                }
                Tag(tag = tag)
            }
        }

        FavouriteToggle(
            isFavorite = isFavorite,
            onToggle = onFavouriteToggle,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RouteTagsWithFavouritePreview() {
    RouteTagsWithFavourite(
        categories = listOf(Category.Culture, Category.Coffee, Category.Nature, Category.Metro),
        isFavorite = true,
        onFavouriteToggle = {}
    )
}

