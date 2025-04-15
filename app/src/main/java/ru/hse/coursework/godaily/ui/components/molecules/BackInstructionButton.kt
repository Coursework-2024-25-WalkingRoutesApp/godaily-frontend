package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily

@Composable
fun BackInstructionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Назад",
        textDecoration = TextDecoration.Underline,
        fontSize = 16.sp,
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = modifier.clickable {
            onClick()
        }
    )
}

@Preview
@Composable
fun BackInstructionButtonPreview() {
    BackInstructionButton({})
}