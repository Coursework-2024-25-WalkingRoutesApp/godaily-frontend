package ru.hse.coursework.godaily.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableBold
import ru.hse.coursework.godaily.ui.components.molecules.AuthCustomField
import ru.hse.coursework.godaily.ui.components.molecules.PasswordCustomField
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.navigation.AuthNavigationItem
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_auth),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 0.dp, y = (-60).dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            VariableBold(
                text = "Войдите в существующий аккаунт",
                fontSize = 35.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))

            AuthCustomField(
                text = viewModel.email,
                placeholder = "Введите адрес электронной почты",
                description = "Почта",
                maxCharacters = null,
                isEmail = true
            )

            Spacer(Modifier.height(27.dp))
            PasswordCustomField(
                text = viewModel.password,
                shouldBeChecked = true
            )

            Spacer(Modifier.height(20.dp))

            StartButton(
                text = "Войти",
                onClick = {
                    coroutineScope.launch {
                        viewModel.loginUser()
                    }
                }
            )
            Spacer(Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    append("Нет аккаунта? ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("Зарегистрироваться")
                    }
                },
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate(AuthNavigationItem.RegisterScreen.route)
                }
            )


        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}
