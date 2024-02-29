package com.whyranoid.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SmallFollowButton(
    textColor: Color = Color.Black,
    backgroundColor: Color,
    text: String,
    onclick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(backgroundColor)
        .clickable {
            onclick()
        }
        .padding(horizontal = 12.dp, vertical = 5.dp)) {
        Text(
            text = text,
            style = WalkieTypography.Body2.copy(
                textColor
            ),
        )
    }
}