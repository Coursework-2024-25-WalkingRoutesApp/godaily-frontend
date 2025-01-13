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
import ru.hse.coursework.godaily.ui.components.organisms.CompletedRoutes
import ru.hse.coursework.godaily.ui.components.organisms.FavouriteRoutes
import ru.hse.coursework.godaily.ui.components.organisms.UserProfile


//TODO: 1) Редактировать профиль побольше размер 2) Обработать переходы на редактирование профиля, пройденные маршруты, Израбнное
@Composable
fun ProfileScreen(
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
                profilePicture = state.profilePicture,
                onEditProfileClick = { viewModel.onEditProfileClicked() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompletedRoutes(
                routeCount = state.completedRoutesCount,
                onClick = { viewModel.onCompletedRoutesClicked() }
            )

            Spacer(modifier = Modifier.width(15.dp))
            FavouriteRoutes(
                routeCount = state.favouriteRoutesCount,
                onClick = { viewModel.onFavouriteRoutesClicked() }
            )
        }
    }
}

