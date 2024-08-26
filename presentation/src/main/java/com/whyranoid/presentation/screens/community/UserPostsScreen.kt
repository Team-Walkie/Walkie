package com.whyranoid.presentation.screens.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.component.community.PostItem
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.CommunityScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserPostScreen(
    user: User,
    postPreviews: List<Post>,
    startIndex: Int,
    onBackPressed: () -> Unit = {},

) {

    val viewModel = koinViewModel<CommunityScreenViewModel>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        style = WalkieTypography.Body1_Normal,
                        text = user.nickname,
                        modifier = Modifier
                            .padding(bottom = 24.dp),
                    )
                    Text(
                        style = WalkieTypography.Title,
                        text = "게시물",
                        modifier = Modifier
                            .padding(bottom = 24.dp),
                    )
                }

            }


            Column {
                postPreviews.forEach { post ->
                    PostItem(
                        post = post,
                        onLikeClicked = { postId ->
                            viewModel.likePost(postId)
                        },
                        onProfileClicked = { user ->
                            // navController.back
                        },
                        onCommentClicked = { post ->
//                            navController.currentBackStackEntry?.savedStateHandle?.set(
//                                "post",
//                                post
//                            )
//                            navController.navigate(Screen.CommentScreen.route)
                        },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun UserPostsScreenPreview() {
    WalkieTheme {
        UserPostScreen(User.DUMMY, listOf(Post.DUMMY), 0)
    }
}