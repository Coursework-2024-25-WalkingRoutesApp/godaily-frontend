package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.molecules.PasswordCustomField

@Composable
fun PasswordColumn(
    password: MutableState<String>,
    passwordAgain: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Box() {
        Column(
            modifier = modifier
        ) {
            PasswordCustomField(text = password, shouldBeChecked = true)
            PasswordCustomField(
                text = passwordAgain,
                placeholder = "Повторите пароль",
                description = "Повторите пароль"
            )
        }
        if (password.value != passwordAgain.value) {
            Text(
                text = "Пароли должны совпадать",
                fontSize = 15.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.BottomStart)
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PasswordColumnPreview() {
    PasswordColumn(
        password = mutableStateOf(""),
        passwordAgain = mutableStateOf("")
    )
}