package com.luteh.ecommerce.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luteh.ecommerce.ui.navigation.MainBottomNav
import com.luteh.ecommerce.ui.navigation.MainBottomNavigationItem
import com.luteh.ecommerce.ui.screen.home.HomeScreen
import com.luteh.ecommerce.ui.screen.profile.ProfileScreen

@Composable
fun MainScreen(
    onNavigateToProductDetailScreen: (id: String) -> Unit,
    onNavigateToLoginScreen: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                MainBottomNavigationItem.items().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
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
            startDestination = MainBottomNav.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainBottomNav.Profile.route) { ProfileScreen(onNavigateToLoginScreen = onNavigateToLoginScreen) }
            composable(MainBottomNav.Home.route) {
                HomeScreen(onNavigateToProductDetailScreen = onNavigateToProductDetailScreen)
            }
        }
    }
}