package com.whyranoid.presentation.component

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.ChallengeColor.getColor

@OptIn(ExperimentalTextApi::class)
@Composable
fun ChallengeGoalContent(
    challenge: Challenge
) {

    val challengeColor = challenge.challengeType.getColor()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
            .background(challengeColor.backgroundColor),
    ) {

        val progressBarColor = challengeColor.progressBarColor

        val progress = requireNotNull(challenge.process) / 100f

        Spacer(modifier = Modifier.height(30.dp))

        val option = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
            inScreenDensity
        }

        val bitmap = BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.run_icon,
            option
        ).asImageBitmap()

        val textMeasure = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .progressSemantics(progress)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
        ) {

            val width = size.width
            val height = size.height

            val yOffset = height / 2

            val isLtr = layoutDirection == LayoutDirection.Ltr
            val barStart = (if (isLtr) 0f else 1f - progress) * width
            val barEnd = (if (isLtr) progress else 1f) * width

            // draw Background
            drawLine(
                Color.White,
                Offset(0f, yOffset),
                Offset(1f * width, yOffset),
                4.dp.toPx(),
                cap = StrokeCap.Round,
            )

            // Progress line
            drawLine(
                progressBarColor,
                Offset(barStart, yOffset),
                Offset(barEnd, yOffset),
                4.dp.toPx(),
                cap = StrokeCap.Round,
            )

            val bitMapIconSize = IntSize(41.dp.toPx().toInt(), 42.dp.toPx().toInt())

            drawImage(
                bitmap,
                dstSize = bitMapIconSize,
                dstOffset = IntOffset(
                    (barEnd - bitMapIconSize.width / 2).toInt(),
                    - bitMapIconSize.height / 2,
                ),
                colorFilter = ColorFilter.tint(progressBarColor)
            )

            val progressText = "${(progress * 100).toInt()}%"

            val progressTextMeasured = textMeasure.measure(
                progressText,
                TextStyle(
                    fontSize = 15.sp,
                )
            )

            drawText(
                textMeasurer = textMeasure, text = progressText,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = challengeColor.progressBarColor
                ),
                topLeft = Offset(
                    width - progressTextMeasured.size.width,
                    yOffset + progressTextMeasured.size.height / 2

                ),
                softWrap = false,
                overflow = TextOverflow.Visible,
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(101.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChallengeGoalItem(
                modifier = Modifier.weight(1f),
                goal = "기간", limit = "${challenge.period}일"
            )

            Divider(
                color = challengeColor.progressBarColor,
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
}