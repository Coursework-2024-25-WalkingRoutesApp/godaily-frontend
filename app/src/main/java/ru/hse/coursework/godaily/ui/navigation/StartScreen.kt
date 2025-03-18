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
    val jwtState = jwtViewModel.jwtFlow.collectAsState(initial = null)

//    //TODO убрать, для тестов
//    jwtViewModel.clearJwt()


    if (jwtState.value != null && jwtViewModel.validateJwt(jwtState.value)) {
        MainScreen(navController)
    } else {
        AuthNavigation(navController)
    }
}