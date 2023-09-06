package com.whyranoid.presentation.screens.challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.util.BLANK
import com.whyranoid.presentation.component.button.WalkieNegativeButton
import com.whyranoid.presentation.component.button.WalkiePositiveButton
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun ChallengeCompleteScreen(
    navController: NavController,
    challengeId: Long,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "챌린지 성공!",
                style = WalkieTypography.Title
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "햄버거 세트 불태우기",
                style = WalkieTypography.SubTitle
            )

            AsyncImage(
                model = "https://picsum.photos/250/250 ", contentDescription = "",
                modifier = Modifier
                    .padding(top = 35.dp)
                    .size(180.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = WalkieTypography.Title.fontSize,
                            fontWeight = WalkieTypography.Title.fontWeight,
                            fontFamily = WalkieTypography.Title.fontFamily,
                            color = WalkieColor.Primary
                        )
                    ) {
                        append("햄버거 세트 뱃지")
                    }
                    append(String.BLANK)
                    withStyle(
                        style = SpanStyle(
                            fontSize = WalkieTypography.Title.fontSize,
                            fontWeight = WalkieTypography.Title.fontWeight,
                            fontFamily = WalkieTypography.Title.fontFamily,
                        )
                    ) {
                        append("획득")
                    }
                },
                modifier = Modifier.padding(top = 24.dp),
            )

            Text(
                modifier = Modifier.padding(top = 11.dp),
                text = "마이페이지에서 확인해보세요",
                style = WalkieTypography.Caption.copy(color = Color(0xFF989898))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = "사진으로 저장하시겠어요?")

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Box(modifier = Modifier.weight(.5f)) {
                    WalkieNegativeButton(text = "아니오") {

                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                Box(modifier = Modifier.weight(.5f)) {
                    WalkiePositiveButton(text = "예") {

                    }
                }
            }
        }


    }
}