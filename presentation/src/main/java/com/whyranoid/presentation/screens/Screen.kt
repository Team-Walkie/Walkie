package com.whyranoid.presentation.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.whyranoid.presentation.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int? = null,
    @DrawableRes val icon: Int? = null,
    @DrawableRes val iconSelected: Int? = null,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Running : Screen(
        "running",
        R.string.running,
        R.drawable.ic_running_screen,
        R.drawable.ic_running_screen_selected,
    )

    object Community : Screen(
        "community",
        R.string.community,
        R.drawable.ic_community_screen,
        R.drawable.ic_community_screen_selected,
    )

    object SearchFriendScreen : Screen(
        "searchFriendScreen",
    )

    object ChallengeMainScreen :
        Screen(
            "challengeMain",
            R.string.challenge_main,
            R.drawable.ic_challenge_screen,
            R.drawable.ic_challenge_screen_selected,
        )

    object MyPage : Screen(
        "myPage",
        R.string.my_page,
        R.drawable.ic_mypage_screen,
        R.drawable.ic_mypage_screen_selected,
    )

    object AddPostScreen : Screen(
        route = "addPostScreen",
    )

    object EditProfileScreen : Screen(
        route = "editProfileScreen",
        arguments = listOf(
            navArgument("imageUrl") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType },
            navArgument("nickName") { type = NavType.StringType },
        ),
    ) {
        const val IMAGE_URL_KEY = "imageUrl"
        const val NAME_KEY = "name"
        const val NICKNAME_KEY = "nickName"
    }

    object ChallengeDetailScreen : Screen(
        route = "challengeDetail/{challengeId}/{isChallenging}",
        arguments = listOf(
            navArgument("challengeId") { type = NavType.LongType },
            navArgument("isChallenging") { type = NavType.BoolType },
        ),
    )

    object ChallengeExitScreen : Screen(
        route = "challengeExit/{challengeId}",
        arguments = listOf(
            navArgument("challengeId") { type = NavType.LongType },
        ),
    )

    object ChallengeCompleteScreen : Screen(
        route = "challengeComplete/{challengeId}",
        arguments = listOf(
            navArgument("challengeId") { type = NavType.LongType },
        ),
    )

    companion object {
        val bottomNavigationItems = listOf(Running, Community, ChallengeMainScreen, MyPage)
    }
}
