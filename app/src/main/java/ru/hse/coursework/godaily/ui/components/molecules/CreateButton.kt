package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.ui.components.atoms.HeaderMedium
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.purpleLight

@Composable
fun CreateButton(
    onClick: () -> Unit,
    text: String = "Создать маршрут",
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        containerColor = purpleLight,
        contentColor = black
    ) {
        HeaderMedium(text = text)
    }
}

@Preview
@Composable
fun CreateButtonPreview() {
    CreateButton(onClick = {})
}
