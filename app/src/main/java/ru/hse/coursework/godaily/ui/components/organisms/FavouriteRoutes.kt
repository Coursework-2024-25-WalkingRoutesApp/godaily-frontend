package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun FavouriteRoutes(routeCount: Int, onClick: () -> Unit = {}) {
    ProfileLittleCard(
        type = "Избранное",
        cardColor = greyLight,
        routeCount = routeCount,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun FavouriteRoutesPreview() {
    FavouriteRoutes(routeCount = 2)
}