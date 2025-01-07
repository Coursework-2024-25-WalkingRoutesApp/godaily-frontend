package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.routeNameBig

@Composable
fun RouteNameBig(text: String, style: TextStyle = routeNameBig) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview
@Composable
fun RouteNameBigPreview() {
    RouteNameBig("Профсоюзная зеленая тропа")
}