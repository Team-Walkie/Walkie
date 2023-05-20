package com.whyranoid.presentation.reusable

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.whyranoid.presentation.theme.WalkieColor

@Composable
fun WalkieCircularProgressIndicator (
    modifier: Modifier = Modifier
) {
    Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = WalkieColor.Primary
        )
    }
}