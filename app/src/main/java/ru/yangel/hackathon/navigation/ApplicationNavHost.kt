package ru.yangel.hackathon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.yangel.hackathon.chat.presentation.ui.ChatScreen
import ru.yangel.hackathon.ai.presentation.screen.AiChatScreen
import ru.yangel.hackathon.login.presentation.screen.LoginScreen
import ru.yangel.hackathon.navigation.bottomNavigation.bottomscreen.MainScreen
import ru.yangel.hackathon.onBoarding.presentation.ui.OnBoardingScreen
import ru.yangel.hackathon.profile.presentation.ProfileScreen
import ru.yangel.hackathon.splash.presentation.ui.screen.SplashScreen
import ru.yangel.hackathon.wishlist.item.presentation.ui.WishlistItemEditScreen
import ru.yangel.hackathon.wishlist.item.presentation.ui.WishlistItemScreen
import ru.yangel.hackathon.wishlist.list.presentation.ui.OtherWishListScreen

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
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("itemCreate") {
            WishlistItemEditScreen(
                onSuccess = navController::popBackStack, onBack = navController::popBackStack
            )
        }
        composable(
            "itemEdit/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) {
            val itemId = it.arguments?.getString("itemId") ?: ""
            WishlistItemEditScreen(itemId = itemId, onSuccess = {
                navController.popBackStack()
                navController.popBackStack()
            }, onBack = navController::popBackStack)
        }
        composable("ai_screen") {
            AiChatScreen()
        }
        composable(
            "itemView/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) {
            val itemId = it.arguments?.getString("itemId") ?: ""
            WishlistItemScreen(itemId = itemId,
                onBack = navController::popBackStack,
                onNavigateToEdit = { navController.navigate("itemEdit/$itemId") })
        }
        composable(
            "otherItemView/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) {
            val itemId = it.arguments?.getString("itemId") ?: ""
            WishlistItemScreen(
                itemId = itemId, onBack = navController::popBackStack, isFromOwnWishlist = false
            )
        }
        composable(
            route = "profileDetails/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileScreen(
                navController = navController,
                userId = backStackEntry.arguments?.getString("id").toString(),
            )
        }
        composable(route = "wishlist/{userId}/{name}/{birthDay}/{avatarUrl}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId").toString()
            val name = backStackEntry.arguments?.getString("name").toString()
            val birthDay = backStackEntry.arguments?.getString("birthDay").toString()
            val avatarUrl = backStackEntry.arguments?.getString("avatarUrl").toString()
            OtherWishListScreen(userId = userId,
                name = name,
                birthDate = birthDay,
                avatarUrl = avatarUrl,
                onItemClick = {
                    navController.navigate("otherItemView/$it")
                },
                onBack = navController::popBackStack,
                onNavigateToChat = { navController.navigate("wishlist/$userId/$name/chat") })
        }

        composable(route = "wishlist/{userId}/{name}/chat") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId").toString()
            val name = backStackEntry.arguments?.getString("name").toString()
            ChatScreen(chatId = userId, name = name)
        }
    }

}