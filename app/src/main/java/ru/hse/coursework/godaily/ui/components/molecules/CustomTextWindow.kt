package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun CustomTextWindow(
    text: MutableState<String>,
    placeholder: String = "Оставьте отзыв...",
    maxCharacters: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val containerBackgroundColor = Color.Transparent
    val textFieldBackgroundColor = Color.White
    val borderColor = greyDark
    val isMaxReached = text.value.length == maxCharacters
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(142.dp)
                .background(textFieldBackgroundColor, RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            val scrollState = rememberScrollState()

            BasicTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= maxCharacters) {
                        text.value = it
                    }
                },
                textStyle = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    color = black,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions.Default,
                decorationBox = { innerTextField ->
                    if (text.value.isEmpty()) {
                        VariableLight(
                            text = placeholder,
                            fontSize = 15.sp,
                            fontColor = greyDark
                        )
                    }
                    innerTextField()
                }
            )
        }

        Text(
            text = "${text.value.length} / $maxCharacters",
            fontSize = 12.sp,
            color = if (isMaxReached) Color.Red else greyDark,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp, end = 10.dp, bottom = 4.dp)
        )
    }
}


@Composable
@Preview
fun CustomTextWindowPreview() {
    CustomTextWindow(
        text = mutableStateOf(""),
    )
}
