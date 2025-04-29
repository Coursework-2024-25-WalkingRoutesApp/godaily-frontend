package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun Tag(tag: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = greyDark,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        VariableLight(text = "#$tag", fontSize = 14.sp, fontColor = greyDark)
    }
}

@Preview
@Composable
fun TagPreview() {
    Tag(tag = "пешеходный")
}
