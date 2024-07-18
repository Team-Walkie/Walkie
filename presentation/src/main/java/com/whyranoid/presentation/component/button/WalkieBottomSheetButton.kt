package com.whyranoid.presentation.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun WalkieBottomSheetButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color(0xFFE4E4E4), shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = buttonText,
            style = WalkieTypography.Body1_Normal,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 6.dp)
        )
    }
}