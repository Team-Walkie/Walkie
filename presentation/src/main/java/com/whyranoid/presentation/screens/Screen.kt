package com.whyranoid.presentation.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.whyranoid.presentation.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int? = null,
    val icon: ImageVector? = null,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Running : Screen("running", R.string.running, Icons.Rounded.DirectionsRun)
    object Community : Screen("community", R.string.community, Icons.Rounded.Forum)
    object ChallengeMainScreen :
        Screen("challengeMain", R.string.challenge_main, Icons.Rounded.MilitaryTech)

    object MyPage : Screen("myPage", R.string.my_page, Icons.Rounded.Person)

    object ChallengeDetailScreen : Screen(
        route = "challengeDetail/{challengeId}/{isChallenging}",
        arguments = listOf(
            navArgument("challengeId") { type = NavType.LongType },
            navArgument("isChallenging") { type = NavType.BoolType },
        ))

    companion object {
        val bottomNavigationItems = listOf(Running, Community, ChallengeMainScreen, MyPage)
    }
}