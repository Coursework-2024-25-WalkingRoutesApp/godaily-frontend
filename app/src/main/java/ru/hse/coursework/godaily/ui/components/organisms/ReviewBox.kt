package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableBold
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.components.molecules.CustomTextWindow
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun ReviewBox(
    feedbackText: MutableState<String>,
    maxCharacters: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val containerBackgroundColor = Color.Transparent
    val textFieldBackgroundColor = Color.White
    val borderColor = greyDark
    val isMaxReached = feedbackText.value.length == maxCharacters

    Column(
        modifier = modifier
            .width(305.dp)
            .background(containerBackgroundColor)
            .padding(15.dp)
    ) {
        VariableBold(text = "Опишите плюсы и минусы", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(15.dp))

        CustomTextWindow(text = feedbackText)
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewBoxPreview() {
    val feedbackText = remember { mutableStateOf("") }
    ReviewBox(feedbackText = feedbackText)
}
