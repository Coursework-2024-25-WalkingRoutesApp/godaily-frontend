package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.routeDescription

@Composable
fun VariableLight(
    text: String,
    fontSize: TextUnit,
    fontColor: Color = black,
    style: TextStyle = routeDescription,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        color = fontColor,
        modifier = modifier
    )
}

@Preview
@Composable
fun VariableLightPreview() {
    VariableLight("Историческое измайлово", 11.sp)
}