package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun CustomTextWindow(
    text: MutableState<String>,
    placeholder: String = "Оставьте отзыв...",
    maxCharacters: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val isMaxReached = text.value.length == maxCharacters

    Column(modifier = modifier) {
        OutlinedTextField(
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
                .height(142.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        color = greyDark
                    )
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = purpleDark,
                cursorColor = black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )

        Text(
            text = "${text.value.length} / $maxCharacters",
            fontSize = 12.sp,
            color = if (isMaxReached) Color.Red else greyDark,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp, end = 4.dp)
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
