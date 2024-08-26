package com.whyranoid.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.component.community.PostItem
import com.whyranoid.presentation.component.running.RunningFollowerItemWithLikable
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.CommunityScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommunityScreen(navController: NavController) {
    val viewModel = koinViewModel<CommunityScreenViewModel>()
    val state by viewModel.collectAsState()

    Scaffold(
        topBar = {
            WalkieTopBar(
                leftContent = {
                    Row {
                        Text(
                            text = "커뮤니티",
                            style = WalkieTypography.Title.copy(fontWeight = FontWeight(600)),
                        )

                        Spacer(modifier = Modifier.width(7.dp))

                        Icon(
                            modifier =
                            Modifier
                                .clickable {
                                },
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Down Arrow",
                        )
                    }
                },
                rightContent = {
                    Row {
                        Icon(
                            modifier =
                            Modifier
                                .clickable {
                                    navController.navigate(Screen.AddPostScreen.route)
                                },
                            imageVector = Icons.Filled.Add,
                            contentDescription = "추가 버튼",
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            modifier =
                            Modifier
                                .clickable {
                                    navController.navigate(Screen.SearchFriendScreen.route)
                                },
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색 버튼",
                        )
                    }
                },
            )
        },
    ) {

        Column(
            modifier = Modifier.padding(it)
        ) {
            LazyRow {
                repeat(10) {
                    item { RunningFollowerItemWithLikable(isDisplayName = true) }
                }
            }

            val refreshing = state.posts.getDataOrNull() == null

            val pullRefreshState = rememberPullRefreshState(
                refreshing = refreshing,
                onRefresh = {
                    viewModel.getPosts()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn {
                    state.posts.getDataOrNull()?.forEach { post ->
                        item {
                            PostItem(
                                post = post,
                                onLikeClicked = { postId ->
                                    viewModel.likePost(postId)
                                },
                                onProfileClicked = { user ->
                                    state.following.getDataOrNull()?.let { followings ->
                                        val isFollowing = followings.contains(user)
                                        navController.navigate("userPage/${user.uid}/${user.nickname}/$isFollowing")
                                    }
                                },
                                onCommentClicked = { post ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "post",
                                        post
                                    )
                                    navController.navigate(Screen.CommentScreen.route)
                                },
                            )
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(androidx.compose.ui.Alignment.TopCenter)
                )
            }
        }
    }
}
