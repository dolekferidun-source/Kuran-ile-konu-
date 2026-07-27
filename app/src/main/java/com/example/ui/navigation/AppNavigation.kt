package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.viewmodel.QuranViewModel

sealed class Screen(val route: String, val title: String, val activeIcon: ImageVector, val inactiveIcon: ImageVector) {
    object Home : Screen("home", "Ana Sayfa", Icons.Filled.Home, Icons.Outlined.Home)
    object Guidance : Screen("guidance", "Ayet Rehberi", Icons.Filled.Search, Icons.Outlined.Search)
    object Scenarios : Screen("scenarios", "Senaryolar", Icons.Filled.MenuBook, Icons.Outlined.MenuBook)
    object Letter : Screen("letter", "Sana Mektup", Icons.Filled.MarkEmailRead, Icons.Outlined.Mail)
    object Mindmap : Screen("mindmap", "Harita", Icons.Filled.Hub, Icons.Outlined.Hub)
    object Journey : Screen("journey", "Yolculuğum", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome)
}

@Composable
fun AppNavigation(
    viewModel: QuranViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarItems = listOf(
        Screen.Home,
        Screen.Scenarios,
        Screen.Letter,
        Screen.Mindmap,
        Screen.Journey
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("app_bottom_navigation")
            ) {
                bottomBarItems.forEach { screen ->
                    val isSelected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) screen.activeIcon else screen.inactiveIcon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = EmeraldPrimary,
                            selectedTextColor = EmeraldPrimary,
                            indicatorColor = GoldAccent.copy(alpha = 0.25f)
                        )
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToGuidance = { navController.navigate(Screen.Guidance.route) },
                    onNavigateToScenarios = { navController.navigate(Screen.Scenarios.route) },
                    onNavigateToChains = { navController.navigate(Screen.Scenarios.route) },
                    onNavigateToLetter = { navController.navigate(Screen.Letter.route) },
                    onNavigateToMindmap = { navController.navigate(Screen.Mindmap.route) }
                )
            }

            composable(Screen.Guidance.route) {
                EmotionGuidanceScreen(
                    viewModel = viewModel,
                    onNavigateToMindmap = { navController.navigate(Screen.Mindmap.route) }
                )
            }

            composable(Screen.Scenarios.route) {
                LifeScenariosScreen(viewModel = viewModel)
            }

            composable(Screen.Letter.route) {
                QuranLetterScreen(viewModel = viewModel)
            }

            composable(Screen.Mindmap.route) {
                VerseMindmapScreen(viewModel = viewModel)
            }

            composable(Screen.Journey.route) {
                SpiritualJourneyScreen(viewModel = viewModel)
            }
        }
    }
}
