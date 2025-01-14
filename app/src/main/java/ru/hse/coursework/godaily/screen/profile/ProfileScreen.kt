package ru.hse.coursework.godaily.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.organisms.CompletedRoutes
import ru.hse.coursework.godaily.ui.components.organisms.FavouriteRoutes
import ru.hse.coursework.godaily.ui.components.organisms.UserProfile
import ru.hse.coursework.godaily.ui.navigation.NavigationItem


//TODO: 1) Редактировать профиль побольше размер 2) Обработать переходы на редактирование профиля, пройденные маршруты, Израбнное
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            UserProfile(
                userName = state.userName,
                profilePictureUrl = state.profilePictureUrl,
                onEditProfileClick = { viewModel.onEditProfileClicked() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompletedRoutes(
                routeCount = state.completedRoutes.count(),
                onClick = { navController.navigate(NavigationItem.CompletedRoutes.route) }
            )

            Spacer(modifier = Modifier.width(15.dp))
            FavouriteRoutes(
                routeCount = state.favouriteRoutes.count(),
                onClick = { navController.navigate(NavigationItem.FavouriteRoutes.route) }
            )
        }
    }
}

