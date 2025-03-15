package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun AuthCustomField(
    text: MutableState<String>,
    placeholder: String = "Имя пользователя",
    description: String = "Имя пользователя",
    maxCharacters: Int? = 20,
    modifier: Modifier = Modifier,
) {
    val textFieldBackgroundColor = Color.White
    val borderColor = greyLight
    val isMaxReached = maxCharacters != null && text.value.length == maxCharacters

    Column {
        VariableMedium(text = description, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(textFieldBackgroundColor, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
                .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 18.dp)
        ) {
            val scrollState = rememberScrollState()

            BasicTextField(
                value = text.value,
                onValueChange = {
                    if (maxCharacters == null || it.length <= maxCharacters) {
                        text.value = it
                    }
                },
                textStyle = TextStyle(
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
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

        if (maxCharacters != null) {
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
}


@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreview() {
    AuthCustomField(
        text = mutableStateOf(""),
    )
}
