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
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun PublishButtonBig(onClick: () -> Unit, text: String = "Опубликовать") {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = lime,
            contentColor = black
        ),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 10.dp,
            end = 16.dp,
            bottom = 10.dp
        ),
        modifier = Modifier
            .width(260.dp)
            .height(46.dp)
    ) {
        RouteNameSmall(text = text)
    }
}

@Preview
@Composable
fun PublishButtonBigPreview() {
    PublishButtonBig(onClick = {})
}