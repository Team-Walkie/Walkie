package com.whyranoid.presentation.reusable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun WalkieTitleBar(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes backIcon: Int = R.drawable.ic_back_arrow,
    trailings: @Composable BoxScope.() -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Icon(
            painter = painterResource(id = backIcon),
            contentDescription = null,
            modifier = Modifier.clickable { onBackClick() }
        )

        Text(
            text = title,
            style = WalkieTypography.Title
        )

        Box {
            trailings()
        }
    }
}

@Preview
@Composable
private fun WalkieTitleBarPreview() {
    WalkieTheme {
        WalkieTitleBar(
            title = "전체 뱃지"
        )
    }
}