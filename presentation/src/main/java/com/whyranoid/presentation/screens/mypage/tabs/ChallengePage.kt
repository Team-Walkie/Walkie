package com.whyranoid.presentation.screens.mypage.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.component.button.WalkiePositiveButton
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun ChallengePage(
    onGotoChallengeClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "챌린지에 도전하고 프로필에 뱃지를 채워보세요!",
                style = WalkieTypography.Body1_Normal.copy(color = Color.Black.copy(alpha = 0.5f))
            )
        }

        Box(modifier = Modifier.padding(20.dp)) {
            WalkiePositiveButton(text = "도전하러 가기") {
                onGotoChallengeClicked()
            }
        }

    }
}
