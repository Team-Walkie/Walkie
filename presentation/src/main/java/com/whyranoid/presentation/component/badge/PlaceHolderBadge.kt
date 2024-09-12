package com.whyranoid.presentation.component.badge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieTheme
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import com.whyranoid.presentation.theme.WalkieColor

@Composable
fun BadgePlaceHolder(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(WalkieColor.GrayDisable)
    ) {
        val pxValue = LocalDensity.current.run { 2.dp.toPx() }
        Canvas(modifier = modifier.size(48.dp)) {
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f),
                style = Stroke(
                    width = pxValue,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )
        }
    }
}

@Composable
@Preview
fun PlaceholderBadgePreview() {
    WalkieTheme {
        BadgePlaceHolder()
    }
}