package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.components.molecules.PauseButton
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton


@Composable
fun RouteNavigationBox(
    modifier: Modifier,
    onNextPointClick: () -> Unit,
    onPauseClick: () -> Unit,
    onNextPointText: String = "К следующей точке",

    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color.White)
    ) {
        PauseButton(
            onClick = onPauseClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )
        PublishButton(
            onClick = onNextPointClick,
            text = onNextPointText,
            modifier = Modifier
                .width(220.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        )
    }
}

@Preview
@Composable
fun RouteNavigationBoxPreview() {
    RouteNavigationBox(
        Modifier,
        onNextPointClick = {},
        onPauseClick = {}
    )
}