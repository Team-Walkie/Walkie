package com.whyranoid.presentation.component.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.post.Post
import com.whyranoid.presentation.icons.buttoniconpack.CommentButtonIcon
import com.whyranoid.presentation.icons.buttoniconpack.HeartButtonIcon
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun PostContentItem(post: Post) {
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
                text = "좋아요 ${post.likeCount}",
                style = WalkieTypography.Body1
            )

            Row {

                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {

                        }
                    ,
                    imageVector = HeartButtonIcon,
                    contentDescription = "좋아요 버튼",
                )

                Spacer(modifier = Modifier.size(16.dp))

                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {

                        }
                    ,
                    imageVector = CommentButtonIcon,
                    contentDescription = "댓글 버튼",
                )
            }
        }

        Spacer(modifier = Modifier.size(13.dp))

        Text(
            text = post.contents,
            style = WalkieTypography.Body1_Normal
        )
    }
}