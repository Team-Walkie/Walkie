package com.whyranoid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.challenge.Challenge

@Composable
fun ChallengeGoalContent(
    challenge: Challenge
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(101.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Color(0xFFF7F7F7)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChallengeGoalItem(
            modifier = Modifier.weight(1f),
            goal = "기간", limit = "${challenge.period}일"
        )

        Divider(
            color = Color(0xFFBABABA),
            modifier = Modifier
                .height(52.dp)
                .width(1.dp)
        )

        // TODO: challenge type
        ChallengeGoalItem(
            modifier = Modifier.weight(1f),
            goal = "칼로리", limit = "1110kcal"
        )

    }
}