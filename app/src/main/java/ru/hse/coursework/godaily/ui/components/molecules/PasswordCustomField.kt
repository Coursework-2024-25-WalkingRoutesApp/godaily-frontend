package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight
import ru.hse.coursework.godaily.ui.theme.purpleDark

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
    val isMaxReached = text.value.length >= maxCharacters
    val isTooShort = shouldBeChecked && text.value.length < 8 && text.value.isNotEmpty()

    Column(modifier = modifier) {
        VariableMedium(text = description, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = text.value,
            onValueChange = {
                if (it.length <= maxCharacters) {
                    text.value = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = black
            ),
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
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль",
                        tint = greyDark
                    )
                }
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isTooShort) {
                        Text(
                            text = "Длина пароля минимум 8 символов",
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Text(
                        text = "${text.value.length} / $maxCharacters",
                        fontSize = 12.sp,
                        color = when {
                            isMaxReached -> Color.Red
                            isTooShort -> Color.Red
                            else -> greyDark
                        }
                    )
                }
            },
            isError = isTooShort,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = purpleDark,
                unfocusedIndicatorColor = greyLight,
                errorIndicatorColor = Color.Red,
                cursorColor = black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PasswordCustomFieldPreview() {
    Column() {
        Spacer(modifier = Modifier.height(500.dp))
        PasswordCustomField(
            text = mutableStateOf("1234567"),
            shouldBeChecked = true
        )
    }
}
