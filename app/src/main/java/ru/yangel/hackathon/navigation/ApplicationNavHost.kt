package ru.yangel.hackathon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.yangel.hackathon.navigation.bottomNavigation.bottomscreen.MainScreen
import ru.yangel.hackathon.onBoarding.presentation.ui.OnBoardingScreen
import ru.yangel.hackathon.splash.presentation.ui.screen.SplashScreen
import ru.yangel.hackathon.wishlist.item.presentation.ui.WishlistItemEditScreen
import ru.yangel.hackathon.wishlist.item.presentation.ui.WishlistItemScreen

@Composable
fun ApplicationNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("onBoarding") {
            OnBoardingScreen(navController)
        }
        composable("bottomNavigation") {
            MainScreen(navController)
        }
        composable("itemCreate") {
            WishlistItemEditScreen(onSuccess = navController::popBackStack, onBack = navController::popBackStack)
        }
        composable("itemEdit/{itemId}", arguments = listOf(navArgument("itemId") { type = NavType.StringType })) {
            val itemId = it.arguments?.getString("itemId") ?: ""
            WishlistItemEditScreen(itemId = itemId, onSuccess = {
                navController.popBackStack()
                navController.popBackStack()
            }, onBack = navController::popBackStack)
        }
        composable("itemView/{itemId}", arguments = listOf(navArgument("itemId") { type = NavType.StringType })) {
            val itemId = it.arguments?.getString("itemId") ?: ""
            WishlistItemScreen(itemId = itemId, onBack = navController::popBackStack, onNavigateToEdit = { navController.navigate("itemEdit/$itemId") })
        }
    }

}