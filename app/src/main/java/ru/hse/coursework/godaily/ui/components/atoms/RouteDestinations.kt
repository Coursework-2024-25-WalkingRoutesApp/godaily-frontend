package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.routeDestinations

@Composable
fun RouteDestinations(start: String, end: String, style: TextStyle = routeDestinations) {

    Text(
        text = "$start — $end",
        style = style,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview
@Composable
fun RouteDestinationsPreview() {
    RouteDestinations("5-ый микрорайон", "Профсоюзная улица, 123")
}