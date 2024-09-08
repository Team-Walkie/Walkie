package com.whyranoid.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.challenge.BadgeInfo
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun BadgeItem(
    badgeInfo: BadgeInfo
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(badgeInfo.image),
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = badgeInfo.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = WalkieTypography.Body2,
            modifier = Modifier
                .widthIn(max = 62.dp)
                .heightIn(max = 24.dp)
        )
    }
}

@Preview
@Composable
private fun BadgePreview() {
    WalkieTheme {
        BadgeItem(
            BadgeInfo(
                1,
                R.drawable.badge_test_2,
                "test"
            )
        )
    }
}