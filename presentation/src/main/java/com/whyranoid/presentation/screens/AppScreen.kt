package com.whyranoid.presentation.screens

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.screens.Screen.Companion.bottomNavigationItems
import com.whyranoid.presentation.screens.challenge.ChallengeDetailScreen
import com.whyranoid.presentation.screens.challenge.ChallengeExitScreen
import com.whyranoid.presentation.screens.challenge.ChallengeMainScreen
import com.whyranoid.presentation.screens.mypage.EditProfileScreen
import com.whyranoid.presentation.screens.mypage.MyPageScreen
import com.whyranoid.presentation.screens.running.RunningScreen
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.util.checkAndRequestPermissions
import com.whyranoid.presentation.util.showPermissionRequestDialog

@Composable
fun AppScreen(startWorker: () -> Unit) {
    val navController = rememberNavController()

    // TODO 권한 요청 위치
    val context = LocalContext.current
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Log.d("test5", "권한이 동의되었습니다.")
        }
        /** 권한 요청시 거부 했을 경우 **/
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("test5", "권한이 거부되었습니다.")
            showPermissionRequestDialog(context)
        }
    }

    SideEffect {
        checkAndRequestPermissions(
            context,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            launcherMultiplePermissions,
        )
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            if (currentDestination?.route in bottomNavigationItems.map { it.route }) {
                BottomNavigation(
                    modifier = Modifier.height(60.dp),
                    backgroundColor = Color.White,
                    elevation = 5.dp,
                ) {
                    bottomNavigationItems.forEach { screen ->
                        val selected =
                            currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    ImageVector.vectorResource(requireNotNull(if (selected) screen.iconSelected else screen.icon)),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                            label = {
                                Text(
                                    stringResource(requireNotNull(screen.resourceId)),
                                    modifier = Modifier.height(15.dp),
                                    color = Color.Black,
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selectedContentColor = WalkieColor.Primary,
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Running.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Running.route) {
                RunningScreen(navController, startWorker)
            }
            composable(Screen.Community.route) {
                CommunityScreen(navController)
            }
            composable(Screen.ChallengeMainScreen.route) {
                ChallengeMainScreen(navController)
            }

            // TODO: repo, viewModel로 하여금 uid를 받아오도록 변경 or MyPageScreen 내부적으로 uid 보관하도록 변경
            composable(Screen.MyPage.route) {
                MyPageScreen(navController, User.DUMMY.uid)
            }

            composable(Screen.EditProfileScreen.route) {
                EditProfileScreen(navController = navController)
            }

            composable(
                Screen.ChallengeDetailScreen.route,
                Screen.ChallengeDetailScreen.arguments,
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val challengeId = arguments.getLong("challengeId")
                val isChallenging = arguments.getBoolean("isChallenging")
                ChallengeDetailScreen(navController, challengeId, isChallenging)
            }
            composable(
                Screen.ChallengeExitScreen.route,
                Screen.ChallengeExitScreen.arguments,
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val challengeId = arguments.getLong("challengeId")
                ChallengeExitScreen(navController, challengeId)
            }
        }
    }
}
