package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun StartScreen(
    jwtViewModel: JwtViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isTokenValid = jwtViewModel.isTokenValid.collectAsState()

    when (isTokenValid.value) {
        true -> MainScreen(navController)
        else -> AuthNavigation(navController)
    }
}