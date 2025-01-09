package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun RouteLengthSmall(distance: String, text: String = "от вас") {
    val baseWidth = 60.dp
    val widthPerChar = 10.dp
    val calculatedWidth = baseWidth + (distance.length * widthPerChar.value).dp

    Box(
        modifier = Modifier
            .width(calculatedWidth)
            .height(20.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(lime),
        contentAlignment = Alignment.Center
    ) {
        VariableMedium(
            text = "$distance $text",
            fontSize = 11.sp,
        )
    }
}

@Preview
@Composable
fun RouteLengthSmallPreview() {
    RouteLengthSmall("0.3 км")
}
