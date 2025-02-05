package ru.hse.coursework.godaily.ui.navigation

import ru.hse.coursework.godaily.R

sealed class BottomNavigationItem(val route: String, val icon: Int, val title: String) {
    data object Home : BottomNavigationItem("home", R.drawable.home, "Главная")
    data object Routes : BottomNavigationItem("reviews", R.drawable.my_routes, "Маршруты")
    data object Profile : BottomNavigationItem("profile", R.drawable.profile, "Профиль")
}