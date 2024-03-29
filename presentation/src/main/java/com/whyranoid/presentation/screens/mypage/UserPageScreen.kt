package com.whyranoid.presentation.screens.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.whyranoid.presentation.screens.Screen
import com.whyranoid.presentation.screens.mypage.following.FollowingViewModel
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun UserPageScreen(
    navController: NavController,
    uid: Long,
    nickname: String,
    isFollowing: Boolean,
) {
    val viewModel = koinViewModel<UserPageViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(uid, isFollowing)
        viewModel.getUserBadges(uid)
        viewModel.getUserPostPreviews(uid)
    }

    val state by viewModel.collectAsState()

    UserPageContent(
        nickname,
        state,
        onDateClicked = viewModel::selectDate,
        onFollowButtonClicked = {
            viewModel.follow(uid)
        },
        onUnFollowButtonClicked = {
            viewModel.unFollow(uid)
        },
        onFollowerCountClicked = {
            navController.navigate(
                Screen.FollowingScreen.route(
                    uid,
                    FollowingViewModel.FOLLOWER_PAGE_NO,
                ),
            )
        },
        onFollowingCountClicked = {
            navController.navigate(
                Screen.FollowingScreen.route(
                    uid,
                    FollowingViewModel.FOLLOWING_PAGE_NO,
                ),
            )
        },
    )
}
