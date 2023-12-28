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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.model.post.TextVisibleState
import com.whyranoid.presentation.reusable.NonLazyGrid
import com.whyranoid.presentation.theme.WalkieColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostPage(
    isMyPage: Boolean = true,
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
        } else if (isMyPage) {
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
    var dynamicFontSize by remember { mutableStateOf(0.sp) }
    var dynamicPaddingSize by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier.onGloballyPositioned {
            val parentSize = it.size
            dynamicFontSize = (parentSize.width / 60f).sp
            dynamicPaddingSize = (parentSize.width / 50f).dp
            it.providedAlignmentLines
        },
    ) {
        AsyncImage(
            model = postPreview.imageUrl,
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
            val selectedTextColor =
                if (postPreview.textVisibleState == TextVisibleState.WHITE) Color.White else Color.Black

            Text(
                text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(postPreview.date)),
                modifier = Modifier
                    .padding(top = dynamicPaddingSize)
                    .align(Alignment.TopCenter),
                color = selectedTextColor,
                fontSize = dynamicFontSize,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dynamicPaddingSize),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                listOf(
                    "DISTANCE\n${postPreview.distanceText}",
                    "TIME\n${postPreview.timeText}",
                    "PACE\n  ${postPreview.paceText}",
                ).forEach {
                    Text(
                        text = it,
                        color = selectedTextColor,
                        fontSize = dynamicFontSize,
                        lineHeight = dynamicFontSize,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
