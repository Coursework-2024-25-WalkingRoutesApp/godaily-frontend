package ru.hse.coursework.godaily.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableBold
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.navigation.AuthNavigationItem
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.purpleDark
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun VerificationScreen(
    navController: NavController,
    type: String,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val codeLength = 6
    val code = viewModel.code
    val focusRequesters = List(codeLength) { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
        keyboardController?.show()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_auth),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 0.dp, y = (-60).dp),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.padding(16.dp)) {
            Back(
                onClick = { navController.navigate(AuthNavigationItem.RegisterScreen.route) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            VariableBold(
                text = "Введите код подтверждения",
                fontSize = 35.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            VariableMedium(
                text = "Введите код подтверждения, отправленный на вашу электронную почту",
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            VariableMedium(
                text = "Проверьте папку «Спам»",
                fontSize = 15.sp,
                fontColor = greyDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 0 until codeLength) {
                    OutlinedTextField(
                        value = code[i],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                val wasEmpty = code[i].isEmpty()
                                code[i] = newValue

                                if (newValue.isNotEmpty() && i < codeLength - 1) {
                                    focusRequesters[i + 1].requestFocus()
                                } else if (newValue.isEmpty() && !wasEmpty && i > 0) {
                                    focusRequesters[i - 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(48.dp)
                            .height(64.dp)
                            .focusRequester(focusRequesters[i]),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = RobotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.Transparent,
                            focusedIndicatorColor = purpleDark,
                            unfocusedIndicatorColor = greyDark,
                            errorIndicatorColor = Color.Red,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ResendCodeBlock(onResendClick = {
                viewModel.wasSent.value = false
                coroutineScope.launch {
                    viewModel.sendCode()
                }
            })

            Spacer(modifier = Modifier.height(16.dp))

            StartButton(
                text = "Подтвердить",
                onClick = {
                    coroutineScope.launch {
                        val isVerified = viewModel.verifyUser(type)

                        if (isVerified && type != "login") {
                            navController.navigate(AuthNavigationItem.AddPhotoScreen.route)
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun ResendCodeBlock(
    cooldownSeconds: Int = 10,
    onResendClick: () -> Unit
) {
    var secondsLeft by remember { mutableStateOf(cooldownSeconds) }
    var canResend by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
        canResend = true
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        VariableMedium("Не получили код?", fontSize = 15.sp)
        Spacer(modifier = Modifier.width(2.dp))

        TextButton(
            onClick = {
                if (canResend) {
                    onResendClick()
                    secondsLeft = cooldownSeconds
                    canResend = false
                    coroutineScope.launch {
                        while (secondsLeft > 0) {
                            delay(1000)
                            secondsLeft--
                        }
                        canResend = true
                    }
                }
            },
            enabled = canResend
        ) {
            VariableMedium(
                text = if (canResend) "Отправить повторно" else "Повторно через $secondsLeft с",
                fontColor = if (canResend) purpleRoutes else greyDark,
                fontSize = 15.sp
            )
        }
    }
}
