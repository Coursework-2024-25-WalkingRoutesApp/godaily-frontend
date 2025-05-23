package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.hse.coursework.godaily.screen.home.HomeScreen
import ru.hse.coursework.godaily.screen.routedetails.RateRouteScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsViewModel
import ru.hse.coursework.godaily.screen.routedetails.RoutePassingScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteReviewsScreen

@Composable
fun HomeNavigation(bottomNavHostController: NavHostController) {
    val homeNavController = rememberNavController()

    val routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel()

    NavHost(
        navController = homeNavController,
        startDestination = NavigationItem.HomeMain.route
    ) {
        composable(NavigationItem.HomeMain.route) {
            HomeScreen(homeNavController, routeDetailsViewModel)
        }
        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteDetailsScreen(homeNavController, routeId, routeDetailsViewModel)
            }
        }
        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteReviewsScreen(homeNavController, routeId, routeDetailsViewModel)
            }
        }

        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RoutePassingScreen(
                    bottomNavHostController,
                    homeNavController,
                    routeId,
                    routeDetailsViewModel
                )
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
                RateRouteScreen(homeNavController, routeId, mark, routeDetailsViewModel)
            }
        }
    }
}