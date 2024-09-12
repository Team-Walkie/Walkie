package com.whyranoid.presentation.reusable

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.challenge.BadgeInfo
import com.whyranoid.presentation.component.badge.BadgePlaceHolder

@Composable
fun MainBadgeItem(
    badgeInfo: BadgeInfo,
    currentState: DragTargetInfo
) {
    Log.d("sm.shin", "mainbadgeInfo: $badgeInfo")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            if (badgeInfo.image == null) {
                BadgePlaceHolder(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF7F7F7))
                        .align(Alignment.Center)
                )

            } else {
                DragTarget(
                    modifier = Modifier.size(54.dp),
                    dataToDrop = badgeInfo,
                    currentState = currentState,
                    content = {
                        Image(
                            painter = painterResource(id = badgeInfo.image),
                            contentDescription = null,
                            modifier = Modifier.size(54.dp)
                        )
                    },
                    placeholder = {
                        BadgePlaceHolder(
                            modifier = Modifier
                                .size(45.dp)
                                .align(Alignment.Center)
                        )
                    }
                )
            }
        }
    }
}
