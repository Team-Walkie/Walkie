package com.whyranoid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.reusable.TextWithImageSpaceBetween
import com.whyranoid.presentation.util.bouncingClickable
import com.whyranoid.presentation.util.conditional

// TODO: set Color System
@Composable
fun ChallengingItem(
    text: String,
    progress: Float,
    badgeImage: Painter,
    onClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .bouncingClickable {
                onClicked()
            }
            .fillMaxWidth()
            .height(105.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFECECEC))
            .conditional(progress == 1f) {
                border(
                    3.dp,
                    Color(0xFFFB8947),
                    shape = RoundedCornerShape(20.dp)
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextWithImageSpaceBetween(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 20.dp),
            text = text,
            image = badgeImage,
            imageSize = 50.dp,
            paddingValues = PaddingValues(end = 10.dp)
        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .padding(horizontal = 26.dp)
                .clip(RoundedCornerShape(15.dp)),
            progress = progress,
            color = Color(0xFFFFB763),
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
