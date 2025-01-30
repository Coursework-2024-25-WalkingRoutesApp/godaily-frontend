package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isRequired: Boolean = false,
    maxLength: Int = 20,
) {

    Column(modifier = modifier) {
        InputField(
            text = text.value,
            onValueChange = { updatedText ->
                if (updatedText.length <= maxLength) {
                    text.value = updatedText
                    onValueChange(updatedText)
                }
            },
            placeholder = placeholder,
            isRequired = isRequired,
            modifier = Modifier.fillMaxWidth()
        )

        CharacterCountIndicator(
            text = text.value,
            maxLength = maxLength,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun InputField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isRequired: Boolean,
    modifier: Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(top = 4.dp)
            .heightIn(min = 20.dp)
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 4f
                )
            },
        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                if (text.isEmpty()) {
                    DisplayPlaceholder(placeholder = placeholder, isRequired = isRequired)
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun DisplayPlaceholder(placeholder: String, isRequired: Boolean) {
    if (isRequired) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            VariableLight(
                text = placeholder,
                fontSize = 20.sp,
                fontColor = greyDark
            )
            Text(
                text = " *",
                color = Color.Red,
                fontSize = 20.sp
            )
        }
    } else {
        VariableLight(
            text = placeholder,
            fontSize = 20.sp,
            fontColor = greyDark
        )
    }
}

@Composable
fun CharacterCountIndicator(text: String, maxLength: Int, modifier: Modifier = Modifier) {
    VariableLight(
        text = "${text.length}/$maxLength",
        fontSize = 14.sp,
        fontColor = greyDark,
        modifier = modifier
            .padding(top = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    var text by remember { mutableStateOf("") }

    CustomTextField(
        modifier = Modifier.padding(16.dp),
        text = mutableStateOf(""),
        onValueChange = { text = it },
        isRequired = true,
        placeholder = "Подсказка",
        maxLength = 100
    )
}
