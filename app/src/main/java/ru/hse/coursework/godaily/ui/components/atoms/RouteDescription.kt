package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.routeDescription

@Composable
fun RouteDescription(text: String, style: TextStyle = routeDescription, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Preview
@Composable
fun RouteDescriptionPreview() {
    RouteDescription(
        "Прогулка по району Измайлово в Москве" +
                " может стать увлекательным и запоминающимся опытом " +
                "для любителей истории, культуры и природы. Также она " +
                "не занимает много времени, что очень удобно!"
    )
}