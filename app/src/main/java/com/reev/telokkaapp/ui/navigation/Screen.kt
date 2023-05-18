package com.reev.telokkaapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Planning : Screen("planning")
    object Profile : Screen("profile")
}