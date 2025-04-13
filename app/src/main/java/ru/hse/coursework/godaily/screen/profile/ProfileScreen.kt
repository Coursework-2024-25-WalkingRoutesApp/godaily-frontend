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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.molecules.AboutButton
import ru.hse.coursework.godaily.ui.components.organisms.CompletedRoutes
import ru.hse.coursework.godaily.ui.components.organisms.FavouriteRoutes
import ru.hse.coursework.godaily.ui.components.organisms.UserProfile
import ru.hse.coursework.godaily.ui.navigation.NavigationItem

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            AboutButton(onClick = { navController.navigate(NavigationItem.AboutProgram.route) })
        }
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserProfile(
                userName = viewModel.userName.value,
                profilePictureUrl = viewModel.profilePictureUrl.value,
                onEditProfileClick = { navController.navigate(NavigationItem.EditProfile.route) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompletedRoutes(
                routeCount = viewModel.completedRoutes.size,
                onClick = { navController.navigate(NavigationItem.CompletedRoutes.route) }
            )

            Spacer(modifier = Modifier.width(15.dp))
            FavouriteRoutes(
                routeCount = viewModel.favouriteRoutes.size,
                onClick = { navController.navigate(NavigationItem.FavouriteRoutes.route) }
            )
        }
    }
}

