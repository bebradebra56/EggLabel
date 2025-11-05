package com.egsi.labsi.sog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.egsi.labsi.sog.navigation.*
import com.egsi.labsi.sog.screen.*
import com.egsi.labsi.sog.ui.theme.EggLabelTheme
import com.egsi.labsi.sog.ui.theme.EggLabelThemeWithState
import com.egsi.labsi.sog.viewmodel.EggLabelViewModel
import com.egsi.labsi.sog.viewmodel.EggLabelViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EggLabelApp()
        }
    }
}

@Composable
fun EggLabelApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: EggLabelViewModel = viewModel(
        factory = EggLabelViewModelFactory(context)
    )
    
    EggLabelThemeWithState(
        isDarkMode = viewModel.isDarkMode
    ) {
        Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                navigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                item.icon, 
                                contentDescription = item.title
                            ) 
                        },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { 
                            it.route == item.screen.route 
                        } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToDecoder = { code ->
                        viewModel.updateSearchQuery(code)
                        navController.navigate(Screen.Decoder.route)
                    },
                    onNavigateToEncyclopedia = {
                        navController.navigate(Screen.Encyclopedia.route)
                    }
                )
            }
            
            composable(Screen.Decoder.route) {
                DecoderScreen(
                    viewModel = viewModel,
                    initialCode = viewModel.searchQuery
                )
            }
            
            composable(Screen.Encyclopedia.route) {
                EncyclopediaScreen()
            }
            
            composable(Screen.Favorites.route) {
                FavoritesScreen(viewModel = viewModel)
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
    }
}
