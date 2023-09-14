package com.whyranoid.presentation.component.bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WalkieTopBar(
    leftContent: @Composable (() -> Unit)? = null,
    middleContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(20.dp, 13.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        leftContent?.let {
            it()
        }
        middleContent?.let {
            it()
        }
        rightContent?.let {
            it()
        }

    }
}