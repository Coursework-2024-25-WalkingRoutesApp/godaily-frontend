package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.core.security.JwtManager
import ru.hse.coursework.godaily.screen.auth.AddPhotoScreen
import ru.hse.coursework.godaily.screen.auth.LoginScreen
import ru.hse.coursework.godaily.screen.auth.RegisterScreen
import ru.hse.coursework.godaily.screen.auth.WelcomeScreen
import javax.inject.Inject

@Composable
fun AuthNavigation(authNavController: NavHostController) {
    NavHost(authNavController, startDestination = AuthNavigationItem.WelcomeScreen.route) {
        composable(AuthNavigationItem.RegisterScreen.route) {
            RegisterScreen(authNavController)
        }
        composable(AuthNavigationItem.LoginScreen.route) {
            LoginScreen(authNavController)
        }
        composable(AuthNavigationItem.AddPhotoScreen.route) {
            AddPhotoScreen(authNavController)
        }
        composable(AuthNavigationItem.WelcomeScreen.route) { // 🔥 Добавляем WelcomeScreen
            WelcomeScreen(authNavController)
        }
    }
}

//TODO логику в ViewModel
@Composable
fun StartScreen(
    startViewModel: StartViewModel = hiltViewModel()
) {
    val navController = rememberNavController()


    val jwtFlow = startViewModel.getJwt().collectAsState(initial = null)

    if (!startViewModel.validateJwt(jwtFlow) /*TODO нормальная проверка*/) {
        AuthNavigation(navController)
    } else {
        MainScreen()
    }
}

//TODO переделать и убрать в другой класс
@HiltViewModel
class StartViewModel @Inject constructor(
    private val jwtManager: JwtManager
) : ViewModel() {
    fun getJwt(): Flow<String?> {
        //TODO убрать
        viewModelScope.launch {
            jwtManager.clearJwt()
        }
        return jwtManager.getJwt()
    }

    fun validateJwt(jwt: State<String?>): Boolean {
        return !jwt.value.isNullOrEmpty()
    }
}
