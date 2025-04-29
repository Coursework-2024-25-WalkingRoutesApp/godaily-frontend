package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.theme.greyLight
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun LoadingScreenWrapper(
    isLoading: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    progressIndicatorColor: Color = purpleDark,
    progressIndicatorStrokeWidth: Dp = 4.dp,
    progressIndicatorTrackColor: Color = greyLight,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.6f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = progressIndicatorColor,
                    strokeWidth = progressIndicatorStrokeWidth,
                    trackColor = progressIndicatorTrackColor,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}