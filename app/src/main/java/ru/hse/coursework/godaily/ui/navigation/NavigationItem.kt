package ru.hse.coursework.godaily.ui.navigation

sealed class NavigationItem(val route: String, val title: String) {
    // Profile
    data object ProfileMain : NavigationItem("profile_main", "Профиль")
    data object CompletedRoutes : NavigationItem("completed_routes", "Пройденные маршруты")
    data object FavouriteRoutes : NavigationItem("favourite_routes", "Избранные маршруты")

    // Route Details
    data object RouteDetails : NavigationItem("route_details", "Детали маршрута")
    data object RouteReviews : NavigationItem("route_reviews", "Отзывы на маршрут")
    data object RouteRate : NavigationItem("route_rate", "Ваш отзыв на маршрут")

    // Home
    data object HomeMain : NavigationItem("home_main", "Главная")

    // Routes
    data object RoutesMain : NavigationItem("routes_main", "Мои маршруты")

    // Map
    data object RouteCreationOnMap :
        NavigationItem("route_creation_on_map", "Создание маршрута на карте")

    data object RouteCreationInfo :
        NavigationItem("route_creation_info", "Заполнение информации о маршруте")

}