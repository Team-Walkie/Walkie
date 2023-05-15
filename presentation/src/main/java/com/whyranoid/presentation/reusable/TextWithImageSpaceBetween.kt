package com.whyranoid.presentation.reusable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TextWithImageSpaceBetween(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    imageSize: Dp,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(imageSize)
                .padding(paddingValues),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
        AsyncImage(model = imageUrl, contentDescription = "뱃지 이미지",
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(imageSize)
        )
    }
}