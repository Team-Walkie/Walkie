package com.whyranoid.presentation.reusable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TextWithCountSpaceBetween(
    modifier: Modifier = Modifier,
    text: String,
    count: Int,
    textStyle: TextStyle,
    countTextStyle: TextStyle,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentWidth().padding(paddingValues),
    ) {
        Text(text = count.toString(), style = countTextStyle)
        Text(text = text, style = textStyle)
    }
}
