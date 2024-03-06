package com.whyranoid.presentation.screens.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SearchedFriendItem(
    modifier: Modifier = Modifier,
    user: User,
    onClickItem: (User) -> Unit = {},
    actionButton: @Composable (User) -> Unit = {},
) {
    Row(
        modifier = modifier.clickable { onClickItem(user) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = user.imageUrl,
            contentDescription = "user image",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(15.dp),
            text = user.nickname,
            style = WalkieTypography.Body2,
        )

        Text(
            text = "·",
            style = WalkieTypography.Body2,
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            actionButton(user)
        }
    }
}
