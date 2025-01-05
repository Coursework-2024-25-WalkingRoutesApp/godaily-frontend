package ru.hse.coursework.godaily.ui.navigation

import ru.hse.coursework.godaily.R

sealed class NavigationItem(val route: String, val icon: Int, val title: String) {
    data object Home : NavigationItem("home", R.drawable.home, "Главная")
    data object Routes : NavigationItem("routes", R.drawable.my_routes, "Маршруты")
    data object Profile : NavigationItem("profile", R.drawable.profile, "Профиль")
}