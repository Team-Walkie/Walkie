package com.whyranoid.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whyranoid.presentation.screens.Screen.Companion.bottomNavigationItems
import com.whyranoid.presentation.screens.challenge.ChallengeDetailScreen
import com.whyranoid.presentation.screens.challenge.ChallengeMainScreen
import com.whyranoid.presentation.theme.WalkieColor

@Composable
fun AppScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            if (currentDestination?.route in bottomNavigationItems.map { it.route }) {
                BottomNavigation(
                    backgroundColor = WalkieColor.Primary,
                ) {
                    bottomNavigationItems.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(requireNotNull(screen.icon), contentDescription = null)
                            },
                            label = { Text(stringResource(requireNotNull(screen.resourceId))) },
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
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Running.route,
            Modifier.padding(innerPadding)
        ) {

            composable(Screen.Running.route) {
                RunningScreen(navController)
            }
            composable(Screen.Community.route) {
                CommunityScreen(navController)
            }
            composable(Screen.ChallengeMainScreen.route) {
                ChallengeMainScreen(navController)
            }
            composable(Screen.MyPage.route) {
                MyPageScreen(navController)
            }

            composable(Screen.ChallengeDetailScreen.route, Screen.ChallengeDetailScreen.arguments) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val challengeId = arguments.getLong("challengeId")
                val isChallenging = arguments.getBoolean("isChallenging")
                ChallengeDetailScreen(navController, challengeId, isChallenging)
            }

        }
    }
}