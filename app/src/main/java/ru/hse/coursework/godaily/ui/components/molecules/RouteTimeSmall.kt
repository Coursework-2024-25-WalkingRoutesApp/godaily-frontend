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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium

@Composable
fun RouteTimeSmall(routeTime: String, modifier: Modifier = Modifier) {
    val baseWidth = 0.dp
    val widthPerChar = 8.dp
    val calculatedWidth = baseWidth + (routeTime.length * widthPerChar.value).dp

    Box(
        modifier = modifier
            .width(calculatedWidth)
            .height(20.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 6.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 6.dp
                )
            )
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        VariableMedium(
            text = routeTime,
            fontSize = 11.sp,
        )
    }
}

@Preview
@Composable
fun RouteTimeSmallPreview() {
    RouteTimeSmall("35-50 минут")
}
