package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.theme.routeNameSmall

@Composable
fun VariableMedium(
    text: String,
    fontSize: TextUnit,
    fontColor: Color = Color.Black,
    style: TextStyle = routeNameSmall
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        color = fontColor
    )
}

@Preview
@Composable
fun VariableMediumPreview() {
    VariableMedium("Историческое измайлово", 11.sp)
}