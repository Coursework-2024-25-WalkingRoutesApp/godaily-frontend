package ru.hse.coursework.godaily.ui.components.atoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.theme.variableBold

@Composable
fun VariableBold(
    text: String,
    fontSize: TextUnit,
    fontColor: Color = Color.Black,
    style: TextStyle = variableBold,
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
fun VariableBoldPreview() {
    VariableBold("Историческое измайлово", 11.sp)
}