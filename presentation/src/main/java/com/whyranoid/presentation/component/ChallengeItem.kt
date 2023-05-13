package com.whyranoid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.util.bouncingClickable

// Todo: set Color System
@Composable
fun ChallengeItem(
    modifier: Modifier = Modifier,
    text: String,
    onClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .bouncingClickable {
                onClicked()
            }
            .fillMaxWidth()
            .height(90.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFECECEC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2)
        }
    }
}