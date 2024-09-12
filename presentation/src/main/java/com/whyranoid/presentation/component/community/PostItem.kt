package com.whyranoid.presentation.component.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.toPostPreview
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.screens.mypage.tabs.PostImagePreview

@Composable
fun PostItem(
    post: Post,
    onLikeClicked: (Long) -> Unit = {},
    onProfileClicked: (User) -> Unit = {},
    onCommentClicked: (Post) -> Unit = {},
    onPostPreviewClicked: (uid: Long, postId: Long) -> Unit = { _, _ -> }
) {
    Column(
        Modifier.fillMaxHeight(),
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
        )

        PostProfileItem(
            post.author,
            post.address,
            onProfileClicked,
        )

        PostImagePreview(
            postPreview = post.toPostPreview(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            onPostPreviewClicked = onPostPreviewClicked
        )

        PostContentItem(
            post = post,
            onLikeClicked = { onLikeClicked(it) },
            onCommentClicked = onCommentClicked
        )
    }
}
