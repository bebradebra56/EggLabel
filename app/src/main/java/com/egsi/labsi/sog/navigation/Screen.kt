package com.egsi.labsi.sog.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Decoder : Screen("decoder")
    object Encyclopedia : Screen("encyclopedia")
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
}

