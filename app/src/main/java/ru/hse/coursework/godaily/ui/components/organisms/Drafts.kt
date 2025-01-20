package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Drafts(routeCount: Int, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    ProfileLittleCard(routeCount = routeCount, onClick = onClick, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun DraftsPreview() {
    Drafts(routeCount = 21)
}
