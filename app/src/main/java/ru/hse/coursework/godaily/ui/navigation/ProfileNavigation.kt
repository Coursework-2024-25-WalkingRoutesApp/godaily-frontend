package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.hse.coursework.godaily.screen.profile.AboutUsScreen
import ru.hse.coursework.godaily.screen.profile.CompletedRoutesScreen
import ru.hse.coursework.godaily.screen.profile.EditProfileScreen
import ru.hse.coursework.godaily.screen.profile.FavouriteRoutesScreen
import ru.hse.coursework.godaily.screen.profile.ProfileScreen
import ru.hse.coursework.godaily.screen.routedetails.RateRouteScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.screen.routedetails.RoutePassingScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteReviewsScreen

@Composable
fun ProfileNavigation(bottomNavHostController: NavHostController) {
    val profileNavController = rememberNavController()

    val routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel()

    NavHost(
        navController = profileNavController,
        startDestination = NavigationItem.ProfileMain.route
    ) {
        composable(NavigationItem.ProfileMain.route) {
            ProfileScreen(profileNavController)
        }
        composable(NavigationItem.CompletedRoutes.route) {
            CompletedRoutesScreen(profileNavController, routeDetailsViewModel)
        }
        composable(NavigationItem.FavouriteRoutes.route) {
            FavouriteRoutesScreen(profileNavController, routeDetailsViewModel)
        }
        composable(NavigationItem.EditProfile.route) {
            EditProfileScreen(profileNavController)
        }
        composable(NavigationItem.AboutProgram.route) {
            AboutUsScreen(profileNavController)
        }
        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteDetailsScreen(profileNavController, routeId, routeDetailsViewModel)
            }
        }
        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RoutePassingScreen(
                    bottomNavHostController,
                    profileNavController,
                    routeId,
                    routeDetailsViewModel
                )
            }
        }
        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteReviewsScreen(profileNavController, routeId, routeDetailsViewModel)
            }
        }
        composable(
            route = NavigationItem.RouteRate.route + "/{routeId}/{averageMark}",
            arguments = listOf(
                navArgument(name = "routeId") {
                    type = NavType.StringType
                },
                navArgument(name = "averageMark") {
                    type = NavType.IntType
                },
            )
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            val mark = backStackEntry.arguments?.getInt("averageMark")
            if (routeId != null && mark != null) {
                RateRouteScreen(profileNavController, routeId, mark, routeDetailsViewModel)
            }
        }
    }
}