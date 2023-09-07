package com.whyranoid.presentation.component.community

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
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
fun PostProfileItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(27.dp)
                .border(1.dp, WalkieColor.Primary, CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://picsum.photos/250/250 ", contentDescription = "내 프로필 이미지",
                modifier = Modifier
                    .size(24.dp)
                    .border(0.5.dp, WalkieColor.GrayBorder, CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.size(11.dp))
        Column {
            Text(
                "tiny_soo_99",
                style = WalkieTypography.Body1
            )
            Text(
                "Seoul, park",
                style = WalkieTypography.Body2
            )
        }
    }
}