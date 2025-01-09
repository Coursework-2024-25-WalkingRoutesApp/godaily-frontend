package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Drafts(routeCount: Int, onClick: () -> Unit = {}) {
    ProfileLittleCard(routeCount = routeCount, onClick = onClick)
}

@Preview(showBackground = true)
@Composable
fun DraftsPreview() {
    Drafts(routeCount = 21)
}
