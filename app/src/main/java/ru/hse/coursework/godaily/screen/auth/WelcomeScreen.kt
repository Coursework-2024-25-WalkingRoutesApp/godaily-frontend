package ru.hse.coursework.godaily.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableBold
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.navigation.AuthNavigationItem
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val isLoading = viewModel.isLoading

    LoadingScreenWrapper(isLoading = isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_auth),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 0.dp, y = (-60).dp),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.background_phones),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = 40.dp)
                    .graphicsLayer(
                        scaleX = 3f,
                        scaleY = 3f,
                        clip = false
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StartButton(
                    onClick = { navController.navigate(AuthNavigationItem.LoginScreen.route) },
                    text = "Войти в аккаунт",
                )
                Spacer(modifier = Modifier.height(16.dp))
                StartButton(
                    onClick = { navController.navigate(AuthNavigationItem.RegisterScreen.route) },
                    text = "Зарегистрироваться",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = greyLight,
                        contentColor = black
                    )
                )
            }

            VariableBold(
                text = "Давай исследовать новые маршруты вместе",
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 70.dp)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = NavController(LocalContext.current))
}
