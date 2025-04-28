package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    placeholder: String,
    isRequired: Boolean = false,
    maxLength: Int = 20,
) {
    OutlinedTextField(
        value = text.value,
        onValueChange = {
            if (it.length <= maxLength) {
                text.value = it
            }
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 17.sp,
            color = black
        ),
        placeholder = {
            DisplayPlaceholder(placeholder, isRequired)
        },
        singleLine = false,
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(1f))
                CharacterCountIndicator(text.value, maxLength, Modifier)
            }

        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = purpleDark,
            unfocusedIndicatorColor = greyLight,
            errorIndicatorColor = Color.Red,
            cursorColor = black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}

@Composable
fun CharacterCountIndicator(text: String, maxLength: Int, modifier: Modifier = Modifier) {
    VariableLight(
        text = "${text.length}/$maxLength",
        fontSize = 12.sp,
        fontColor = when {
            (text.length == maxLength) -> Color.Red
            else -> greyDark
        },
        modifier = modifier
            .padding(top = 4.dp)
    )
}

@Composable
fun DisplayPlaceholder(placeholder: String, isRequired: Boolean) {
    if (isRequired) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            VariableLight(
                text = placeholder,
                fontSize = 17.sp,
                fontColor = greyDark
            )
            Text(
                text = " *",
                color = Color.Red,
                fontSize = 17.sp
            )
        }
    } else {
        VariableLight(
            text = placeholder,
            fontSize = 17.sp,
            fontColor = greyDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    var text by remember { mutableStateOf("") }

    CustomTextField(
        modifier = Modifier.padding(16.dp),
        text = mutableStateOf(""),
        isRequired = true,
        placeholder = "Подсказка",
        maxLength = 10
    )
}
