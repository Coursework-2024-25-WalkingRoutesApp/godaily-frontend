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
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun RouteTimeBig(routeTime: String) {
    val baseWidth = 15.dp
    val widthPerChar = 10.dp
    val calculatedWidth = baseWidth + (routeTime.length * widthPerChar.value).dp

    Box(
        modifier = Modifier
            .width(calculatedWidth)
            .height(30.dp)
            .clip(RoundedCornerShape(29.dp))
            .background(greyLight),
        contentAlignment = Alignment.Center
    ) {
        VariableMedium(
            text = routeTime,
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun RouteTimeBigPreview() {
    RouteTimeBig("30-45 минут")
}
