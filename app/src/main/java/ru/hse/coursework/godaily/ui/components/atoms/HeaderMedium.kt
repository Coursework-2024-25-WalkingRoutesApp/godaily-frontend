package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.headerMedium

@Composable
fun HeaderMedium(text: String, style: TextStyle = headerMedium) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview
@Composable
fun HeaderMediumPreview() {
    HeaderMedium("Продолжи маршрут")
}