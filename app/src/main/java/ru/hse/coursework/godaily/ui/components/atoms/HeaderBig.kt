package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.coursework.godaily.ui.theme.headerBig

@Composable
fun HeaderBig(text: String, style: TextStyle = headerBig, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Preview
@Composable
fun HeaderBigPreview() {
    HeaderBig("Маршруты для вас")
}