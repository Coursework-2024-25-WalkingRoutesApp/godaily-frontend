package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium

@Composable
fun Exit(
    onClick: () -> Unit,
    text: String = "Выйти из приложения",
    contentColor: Color = Color(0xFFBB1414)
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier
            .height(23.dp)
    ) {
        VariableMedium(text, 15.sp, contentColor)
    }
}

@Preview
@Composable
fun ExitPreview() {
    Exit(onClick = {})
}