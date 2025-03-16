package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.components.atoms.HeaderMedium
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun StartButton(
    onClick: () -> Unit,
    text: String = "В путь",
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = lime,
        contentColor = black
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = colors,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 10.dp,
            end = 16.dp,
            bottom = 10.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        HeaderMedium(text = text)
    }
}

@Preview
@Composable
fun StartButtonPreview() {
    StartButton(onClick = {})
}
