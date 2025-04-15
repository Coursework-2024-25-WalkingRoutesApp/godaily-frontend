package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun PasswordCustomField(
    text: MutableState<String>,
    placeholder: String = "Пароль",
    description: String = "Пароль",
    maxCharacters: Int = 20,
    shouldBeChecked: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val textFieldBackgroundColor = Color.White
    val borderColor = greyLight
    val isMaxReached = text.value.length == maxCharacters

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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        fontSize = 16.sp,
                        color = black,
                    ),
                    modifier = Modifier.weight(1f),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
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
                    },
                    singleLine = true
                )
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль",
                    tint = greyDark,
                    modifier = Modifier
                        .clickable { isPasswordVisible = !isPasswordVisible }
                        .padding(start = 8.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (shouldBeChecked && text.value.length < 8 && text.value.isNotEmpty()) {
                Text(
                    text = "Длина пароля минимум 8 символов",
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${text.value.length} / $maxCharacters",
                fontSize = 12.sp,
                color = if (isMaxReached) Color.Red else greyDark,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PasswordCustomFieldPreview() {
    PasswordCustomField(
        text = mutableStateOf("1234567"),
        shouldBeChecked = true
    )
}
