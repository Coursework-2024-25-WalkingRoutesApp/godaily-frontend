package ru.hse.coursework.godaily.ui.navigation

sealed class NavigationItem(val route: String, val title: String) {
    data object ProfileMain : NavigationItem("profile_main", "Профиль")
    data object CompletedRoutes : NavigationItem("completed_routes", "Пройденные маршруты")
    data object FavouriteRoutes : NavigationItem("favourite_routes", "Избранные маршруты")
}