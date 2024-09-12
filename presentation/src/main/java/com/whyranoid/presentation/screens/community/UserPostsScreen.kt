package com.whyranoid.presentation.screens.community

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.component.community.PostItem
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.CommunityScreenViewModel
import com.whyranoid.presentation.viewmodel.UserPostUiState
import com.whyranoid.presentation.viewmodel.UserPostsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserPostScreen(
    uid: Long,
    postId: Long,
    onProfileClicked: (user: User) -> Unit,
    onCommentClicked: (post: Post) -> Unit,
    onBackPressed: () -> Unit = {},
) {

    val viewModel = koinViewModel<UserPostsViewModel>()
    val communityViewModel = koinViewModel<CommunityScreenViewModel>()
    val userPostUiState = viewModel.userPostUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uid) {
        viewModel.getUiData(uid)
    }

    when (userPostUiState.value) {
        UserPostUiState.Loading -> {
            Log.d("ju0828", "Loading")
            /* todo progressbar*/
        }

        UserPostUiState.Failed -> {
            Log.d("ju0828", "Failed")
            /* todo progressbar */
        }

        is UserPostUiState.Success -> {
            val uiData = (userPostUiState.value as UserPostUiState.Success)
            UserPostUi(
                uiData.user,
                uiData.posts,
                postId,
                communityViewModel::likePost,
                onProfileClicked,
                onCommentClicked,
                onBackPressed
            )
        }
    }

}

@Composable
fun UserPostUi(
    user: User,
    posts: List<Post>,
    postId: Long,
    onClickLikePost: (postId: Long) -> Unit,
    onProfileClicked: (user: User) -> Unit,
    onCommentClicked: (post: Post) -> Unit,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Icon(
                    modifier =
                    Modifier
                        .padding(start = 12.dp)
                        .padding(bottom = 12.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onBackPressed() },
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Left Arrow",
                )
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        style = WalkieTypography.Body1_Normal,
                        text = user.nickname,
                    )
                    Text(
                        style = WalkieTypography.Title,
                        text = "게시물",
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                    )
                }

            }


            val scrollState = rememberLazyListState()
            LaunchedEffect(key1 = postId) {
                scrollState.scrollToItem(
                    maxOf(0, posts.indexOfFirst { it.id == postId }),
                )
            }
            LazyColumn(
                state = scrollState,
            ) {
                posts.forEach { post ->
                    item {
                        PostItem(
                            post = post,
                            onLikeClicked = { postId ->
                                onClickLikePost(postId)
                            },
                            onProfileClicked = { clickedUser ->
                                if (clickedUser.uid == user.uid) {
                                    onBackPressed()
                                } else {
                                    onProfileClicked(user)
                                }
                            },
                            onCommentClicked = { post ->
                                onCommentClicked(post)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun UserPostsScreenPreview() {
    WalkieTheme {
        UserPostScreen(User.DUMMY.uid, 0, {}, {}, {})
    }
}