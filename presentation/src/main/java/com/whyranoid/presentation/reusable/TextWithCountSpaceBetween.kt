package com.whyranoid.presentation.reusable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithCountSpaceBetween(
    modifier: Modifier = Modifier,
    text: String,
    count: Int,
    textSize: Int,
    countSize: Int,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentWidth().padding(paddingValues),
    ) {
        Text(fontSize = countSize.sp, text = count.toString(), fontWeight = FontWeight.Bold)
        Text(fontSize = textSize.sp, text = text)
    }
}
