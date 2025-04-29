package ru.hse.coursework.godaily.ui.components.molecules

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
fun AuthCustomField(
    text: MutableState<String>,
    placeholder: String = "Имя пользователя",
    description: String = "Имя пользователя",
    isEmail: Boolean = false,
    maxCharacters: Int? = 20,
) {
    val isMaxReached = maxCharacters != null && text.value.length == maxCharacters
    val isEmailValid =
        !isEmail || text.value.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(text.value).matches()

    Column {
        VariableMedium(text = description, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
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
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (!isEmailValid) {
                        Text(
                            text = "Некорректный email",
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (maxCharacters != null) {
                        Text(
                            text = "${text.value.length} / $maxCharacters",
                            fontSize = 12.sp,
                            color = if (isMaxReached) Color.Red else greyDark
                        )
                    }
                }
            },
            isError = !isEmailValid,
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreview() {
    Column() {
        Spacer(modifier = Modifier.height(500.dp))
        AuthCustomField(
            text = mutableStateOf("1@gmail.com"),
            isEmail = true,
            maxCharacters = 30
        )
    }

}
