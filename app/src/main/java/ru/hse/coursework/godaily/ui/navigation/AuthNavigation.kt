package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.hse.coursework.godaily.screen.auth.AddPhotoScreen
import ru.hse.coursework.godaily.screen.auth.AuthViewModel
import ru.hse.coursework.godaily.screen.auth.LoginScreen
import ru.hse.coursework.godaily.screen.auth.RegisterScreen
import ru.hse.coursework.godaily.screen.auth.WelcomeScreen

@Composable
fun AuthNavigation(authNavController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(authNavController, startDestination = AuthNavigationItem.WelcomeScreen.route) {
        composable(AuthNavigationItem.RegisterScreen.route) {
            authViewModel.clear()
            RegisterScreen(authNavController, authViewModel)
        }
        composable(AuthNavigationItem.LoginScreen.route) {
            LoginScreen(authNavController)
        }
        composable(AuthNavigationItem.AddPhotoScreen.route) {
            AddPhotoScreen(authNavController, authViewModel)
        }
        composable(AuthNavigationItem.WelcomeScreen.route) { // 🔥 Добавляем WelcomeScreen
            WelcomeScreen(authNavController)
        }
    }
}



