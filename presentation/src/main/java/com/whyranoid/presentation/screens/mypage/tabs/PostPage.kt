package com.whyranoid.presentation.screens.mypage.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.model.post.TextVisibleState
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.reusable.NonLazyGrid
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import java.text.SimpleDateFormat
import java.util.*

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
            PostImagePreview(
                postPreview = postPreviews[index],
                onPostPreviewClicked = onPostPreviewClicked,
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

@Composable
fun PostImagePreview(
    modifier: Modifier = Modifier,
    postPreview: PostPreview,
    onPostPreviewClicked: (id: Long) -> Unit = {},
) {
    // TODO modifier 크기 맞춰서 글자 크기 수정
    Box(modifier = modifier) {
        AsyncImage(
            model = User.DUMMY.imageUrl, // TODO REMOVE postPreview.imageUrl,
            contentDescription = "postPreview Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    onPostPreviewClicked(postPreview.id)
                },
            contentScale = ContentScale.Crop,
        )

        if (postPreview.textVisibleState != TextVisibleState.HIDE) {
            val textColor =
                if (postPreview.textVisibleState == TextVisibleState.WHITE) Color.White else Color.Black

            Text(
                text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(postPreview.date)),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.TopCenter),
                style = WalkieTypography.Body2.copy(color = textColor),
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                listOf(
                    "DISTANCE\n${postPreview.distanceText}",
                    "TIME\n${postPreview.timeText}",
                    "PACE\n${postPreview.paceText}",
                ).forEach {
                    Text(
                        it,
                        style = WalkieTypography.Title.copy(color = textColor),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
