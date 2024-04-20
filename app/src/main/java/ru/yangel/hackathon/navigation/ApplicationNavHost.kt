package ru.yangel.hackathon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.yangel.hackathon.navigation.bottomNavigation.bottomscreen.MainScreen
import ru.yangel.hackathon.onBoarding.presentation.ui.OnBoardingScreen
import ru.yangel.hackathon.splash.presentation.ui.screen.SplashScreen

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
            MainScreen()
        }
    }

}