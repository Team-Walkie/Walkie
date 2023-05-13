package com.whyranoid.presentation.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.whyranoid.presentation.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Running : Screen("running", R.string.running, Icons.Rounded.DirectionsRun)
    object Community : Screen("community", R.string.community, Icons.Rounded.Forum)
    object ChallengeMainScreen : Screen("challengeMain", R.string.challenge_main, Icons.Rounded.MilitaryTech)
    object MyPage : Screen("myPage", R.string.my_page, Icons.Rounded.Person)

    companion object {
        val bottomNavigationItems = listOf(Running, Community, ChallengeMainScreen, MyPage)
    }
}