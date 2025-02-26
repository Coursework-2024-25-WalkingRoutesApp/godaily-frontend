package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.components.atoms.RouteDescription

@Composable
fun ScrollableDescription(
    description: String,
    modifier: Modifier = Modifier.padding(start = 16.dp, end = 16.dp),
    color: Color = Color.Black
) {
    Box(
        modifier = modifier
            .height(130.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        RouteDescription(text = description, modifier = modifier, color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableDescriptionPreview() {
    ScrollableDescription(
        description = "Прогулка по району Измайлово в Москве может стать увлекательным и запоминающимся опытом для любителей истории, культуры и природы. Также она не затруднит передвижение..."
    )
}
