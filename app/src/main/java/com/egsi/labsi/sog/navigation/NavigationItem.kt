package com.egsi.labsi.sog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    val title: String
)

val navigationItems = listOf(
    NavigationItem(Screen.Home, Icons.Default.Home, "Home"),
    NavigationItem(Screen.Decoder, Icons.Default.Search, "Decode"),
    NavigationItem(Screen.Encyclopedia, Icons.AutoMirrored.Filled.MenuBook, "Guide"),
    NavigationItem(Screen.Favorites, Icons.Default.Favorite, "Favorites"),
    NavigationItem(Screen.Settings, Icons.Default.Settings, "Settings")
)

