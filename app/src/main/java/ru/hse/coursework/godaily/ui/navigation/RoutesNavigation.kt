package ru.hse.coursework.godaily.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.hse.coursework.godaily.screen.map.CreateRouteInfoScreen
import ru.hse.coursework.godaily.screen.map.CreateRouteMapScreen
import ru.hse.coursework.godaily.screen.routedetails.RateRouteScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteDetailsScreen
import ru.hse.coursework.godaily.screen.routedetails.RoutePassingScreen
import ru.hse.coursework.godaily.screen.routedetails.RouteReviewsScreen
import ru.hse.coursework.godaily.screen.routes.RoutesScreen

@Composable
fun RoutesNavigation(bottomNavHostController: NavHostController) {
    val routesNavController = rememberNavController()
    NavHost(
        navController = routesNavController,
        startDestination = NavigationItem.RoutesMain.route
    ) {
        composable(NavigationItem.RoutesMain.route) {
            RoutesScreen(routesNavController)
        }
        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteDetailsScreen(routesNavController, routeId)
            }
        }
        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RouteReviewsScreen(routesNavController, routeId)
            }
        }
        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            if (routeId != null) {
                RoutePassingScreen(routesNavController, routeId)
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
                RateRouteScreen(routesNavController, routeId, mark)
            }
        }

        composable(NavigationItem.RouteCreationOnMap.route) {
            CreateRouteMapScreen(routesNavController)
        }

        composable(NavigationItem.RouteCreationInfo.route) {
            CreateRouteInfoScreen(routesNavController, bottomNavHostController)
        }
    }
}