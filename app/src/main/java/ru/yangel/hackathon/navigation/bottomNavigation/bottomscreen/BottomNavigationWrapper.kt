package ru.yangel.hackathon.navigation.bottomNavigation.bottomscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.yangel.hackathon.R
import ru.yangel.hackathon.calendar.presentation.screen.CalendarScreen
import ru.yangel.hackathon.follows.presentation.ui.screen.SearchScreen
import ru.yangel.hackathon.navigation.utils.noRippleClickable
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.SuvaGray
import ru.yangel.hackathon.wishlist.list.presentation.ui.OwnWishListScreen

sealed class BottomBarRoutes(
    val route: String,
    val icon: Int,
) {
    data object Calendar : BottomBarRoutes(
        route = "calendar", icon = R.drawable.calendar_icon
    )

    data object WishList : BottomBarRoutes(
        route = "whishlist",
        icon = R.drawable.whishlist_icon,
    )

    data object Search : BottomBarRoutes(
        route = "search",
        icon = R.drawable.search_icon,
    )

    data object Follows : BottomBarRoutes(
        route = "follows",
        icon = R.drawable.follows_icon,
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        BottomBarRoutes.Calendar,
        BottomBarRoutes.WishList,
        BottomBarRoutes.Search,
        BottomBarRoutes.Follows
    )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp),
        tonalElevation = 0.dp,
        containerColor = Color.White,
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarRoutes, currentDestination: NavDestination?, navController: NavHostController
) {
    val color = if (currentDestination?.route == screen.route) Primary else SuvaGray

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .noRippleClickable {
                if (currentDestination?.route != screen.route) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }) {
        Icon(
            imageVector = ImageVector.vectorResource(screen.icon),
            tint = color,
            modifier = Modifier.size(21.dp),
            contentDescription = "Navigation icon",
        )
    }
}

@Composable
fun MainScreen(
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != "profileDetails/{id}") {
                BottomBar(navController = navController)
            }
        }
    ) {
        BottomNavigation(
            bottomNavigationNavController = navController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(it)
        )
    }
}


@Composable
fun BottomNavigation(
    bottomNavigationNavController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = bottomNavigationNavController,
        modifier = modifier,
        startDestination = "calendar"
    ) {
        composable("calendar") {
            CalendarScreen(onNavigateToProfile = {
                rootNavController.navigate("profileDetails/$it")
            })
        }
        composable("whishlist") {
            OwnWishListScreen(onItemClick = {
                rootNavController.navigate("itemView/$it")
            }, onAddClick = {
                rootNavController.navigate("itemCreate")
            })
        }
        composable("search") {
            SearchScreen(rootNavController = rootNavController)
        }
        composable("follows") {
            SearchScreen(rootNavController = rootNavController)
        }
    }
}
