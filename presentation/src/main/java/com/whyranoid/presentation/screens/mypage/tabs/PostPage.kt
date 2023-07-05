package com.whyranoid.presentation.screens.mypage.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.presentation.reusable.NonLazyGrid
import com.whyranoid.presentation.theme.WalkieColor

@Composable
fun PostPage(
    postPreviews: List<PostPreview>,
    onPostPreviewClicked: (id: Long) -> Unit,
    onPostCreateClicked: () -> Unit,
) {
    NonLazyGrid(
        columns = 3,
        itemCount = postPreviews.size + 1,
        contentPadding = 4,
    ) { index ->
        if (index < postPreviews.size) {
            AsyncImage(
                model = postPreviews[index].imageUrl,
                contentDescription = "postPreview Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        onPostPreviewClicked(postPreviews[index].id)
                    },
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        width = 2.dp,
                        color = WalkieColor.GrayDefault,
                        shape = RectangleShape,
                    )
                    .clickable {
                        onPostCreateClicked()
                    },
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(22.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "포스트 생성",
                    tint = WalkieColor.GrayDefault,
                )
            }
        }
    }
}
