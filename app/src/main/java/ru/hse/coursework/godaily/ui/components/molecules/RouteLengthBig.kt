package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun RouteLengthBig(distance: String, text: String = "от вас") {
    val baseWidth = 100.dp
    val widthPerChar = 10.dp
    val calculatedWidth = baseWidth + (distance.length * widthPerChar.value).dp

    Box(
        modifier = Modifier
            .width(calculatedWidth)
            .height(39.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(lime), // Фон
        contentAlignment = Alignment.Center
    ) {
        VariableMedium(
            text = "$distance $text",
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun RouteLengthBigPreview() {
    RouteLengthBig("0.3 км")
}
