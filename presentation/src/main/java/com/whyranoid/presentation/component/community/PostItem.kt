package com.whyranoid.presentation.component.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.Post

@Composable
fun PostItem(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        PostProfileItem(post.author)

        AsyncImage(
            model = post.imageUrl, contentDescription = "게시글 사진",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        PostContentItem(post)

    }
}