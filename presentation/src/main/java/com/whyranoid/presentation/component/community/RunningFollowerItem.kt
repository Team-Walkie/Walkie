package com.whyranoid.presentation.component.community

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun RunningFollowerItem() {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .size(76.dp)
                .border(2.dp, WalkieColor.Primary, CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://picsum.photos/250/250 ", contentDescription = "달리고 있는 친구의 프로필 이미지",
                modifier = Modifier
                    .size(65.dp)
                    .border(0.5.dp, WalkieColor.GrayBorder, CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.size(2.dp))

        Text(text = "내 기록")
    }
}
