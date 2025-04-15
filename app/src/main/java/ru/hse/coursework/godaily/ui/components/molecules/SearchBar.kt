package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun SearchBar(
    text: MutableState<String>,
    placeholder: String = "Поиск",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .width(341.dp)
            .height(42.dp)
            .background(
                color = greyLight,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (text.value.isEmpty()) {
                    VariableLight(
                        text = placeholder,
                        fontSize = 16.sp,
                        fontColor = greyDark,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                BasicTextField(
                    value = text.value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = greyDark,
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (text.value.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = "Очистить поиск",
                    tint = greyDark,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            text.value = ""
                            onValueChange("")
                        }
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    val text = mutableStateOf("")
    SearchBar(
        text = text,
        onValueChange = { text.value = it }
    )
}
