package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameSmall
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun PauseButton(onClick: () -> Unit, text: String = "Пауза", modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = purpleDark,
            contentColor = black
        ),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 5.dp,
            end = 16.dp,
            bottom = 5.dp
        ),
        modifier = modifier
            .width(90.dp)
            .height(34.dp)
    ) {
        RouteNameSmall(text = text)
    }
}

@Preview
@Composable
fun PauseButtonPreview() {
    PauseButton(onClick = {})
}