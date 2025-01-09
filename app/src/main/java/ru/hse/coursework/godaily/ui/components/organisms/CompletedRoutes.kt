package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun CompletedRoutes(routeCount: Int, onClick: () -> Unit = {}) {
    ProfileLittleCard(
        type = "Пройденные маршруты",
        cardColor = lime,
        routeCount = routeCount,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun CompletedRoutesPreview() {
    CompletedRoutes(routeCount = 2)
}