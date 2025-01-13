package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.hse.coursework.godaily.screen.home.HomeScreen
import ru.hse.coursework.godaily.screen.profile.ProfileScreen
import ru.hse.coursework.godaily.screen.routes.RoutesScreen
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Routes,
        NavigationItem.Profile
    )

    NavigationBar(
        containerColor = Color.White

    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = purpleDark,
                    unselectedIconColor = greyDark,
                    indicatorColor = Color.Transparent
                )
            )

        }
    }

}


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Routes.route) { RoutesScreen() }
        composable(NavigationItem.Profile.route) { ProfileScreen() }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(navController)
        }
    }
}

