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
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SearchedFriendItem(
    modifier: Modifier = Modifier,
    nickname: String
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            model = "https://picsum.photos/250/250 ", contentDescription = "",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(15.dp),
            text = nickname,
            style = WalkieTypography.Body2
        )

        Text(
            text = "·",
            style = WalkieTypography.Body2
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            modifier = Modifier.clickable {

            },
            text = "팔로우",
            style = WalkieTypography.Body2.copy(
                color = WalkieColor.Primary
            )
        )

    }
}