package com.whyranoid.presentation.screens.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserWithFollowingState
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SearchedFriendItem(
    modifier: Modifier = Modifier,
    userWithFollowingState: UserWithFollowingState,
    onClickFollow: (User) -> Unit = {},
    onClickUnFollow: (User) -> Unit = {},
    onClickItem: (User) -> Unit = {},
) {
    // var isFollowing by remember { mutableStateOf(userWithFollowingState.isFollowing) }

    Row(
        modifier = modifier.clickable { onClickItem(userWithFollowingState.user) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = userWithFollowingState.user.imageUrl,
            contentDescription = "user image",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(15.dp),
            text = userWithFollowingState.user.nickname,
            style = WalkieTypography.Body2,
        )

        Text(
            text = "·",
            style = WalkieTypography.Body2,
        )

        Spacer(modifier = Modifier.width(5.dp))

//        Text(
//            modifier = Modifier.clickable {
//                if (isFollowing) {
//                    onClickUnFollow(userWithFollowingState.user)
//                } else {
//                    onClickFollow(
//                        userWithFollowingState.user,
//                    )
//                }
//                isFollowing = isFollowing.not()
//            },
//            text = if (isFollowing) {
//                "언팔로우"
//            } else {
//                "팔로우"
//            },
//            style = WalkieTypography.Body2.copy(
//                color = WalkieColor.Primary,
//            ),
//        )
    }
}
