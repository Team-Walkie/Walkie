package com.whyranoid.presentation.component.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.post.Post
import com.whyranoid.presentation.R
import com.whyranoid.presentation.component.text.ExpandableText
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun PostContentItem(
    post: Post,
    onLikeClicked: (Long) -> Unit = {},
    onCommentClicked: (Post) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 13.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = post.author.nickname,
                style = WalkieTypography.Body1
            )

            Row {

                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .clickable {
                            onLikeClicked(post.id)
                        }
                        .padding(2.dp),
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "좋아요 버튼",
                    tint = if (post.isLiked) WalkieColor.Primary else WalkieColor.GrayBorder
                )

                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = post.likeCount.toString(),
                    style = WalkieTypography.Body1_Normal,
                    color = WalkieColor.GrayBorder
                )


                Spacer(modifier = Modifier.size(4.dp))

                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .clickable {
                            onCommentClicked(post)
                        }
                        .padding(3.dp),
                    painter = painterResource(id = R.drawable.ic_comment_button),
                    contentDescription = "댓글 버튼",
                    tint = WalkieColor.GrayBorder
                )

                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = post.commentCount.toString(),
                    style = WalkieTypography.Body1_Normal,
                    color = WalkieColor.GrayBorder
                )
            }
        }

        Spacer(modifier = Modifier.size(13.dp))

        ExpandableText(text = post.contents, style = WalkieTypography.Body1_Normal)
    }
}

