package ru.yangel.hackathon.navigation.bottomNavigation

import androidx.annotation.DrawableRes


data class BottomNavItem(
    @DrawableRes
    val iconId: Int,
    val route: String
)