package com.whyranoid.presentation.screens.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.whyranoid.domain.model.challenge.BadgeInfo
import com.whyranoid.presentation.R
import com.whyranoid.presentation.reusable.BadgeItem
import com.whyranoid.presentation.reusable.DragTargetInfo
import com.whyranoid.presentation.reusable.DropTarget
import com.whyranoid.presentation.reusable.LongPressDraggable
import com.whyranoid.presentation.reusable.MainBadgeItem
import com.whyranoid.presentation.reusable.WalkieTitleBar
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.TotalBadgeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TotalBadgeScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<TotalBadgeViewModel>()

    val dragTargetInfo = remember { DragTargetInfo() }

    LongPressDraggable(state = dragTargetInfo) {

        // TODO remove
        val mainBadgeList = remember {
            mutableStateListOf(
                BadgeInfo(1, R.drawable.badge_test, "badge 1"),
                BadgeInfo(2, R.drawable.badge_test, "badge 2"),
                BadgeInfo(3, R.drawable.badge_test, "badge 3"),
                BadgeInfo(4, R.drawable.badge_test, "badge 4"),
                BadgeInfo(5, R.drawable.badge_test, "badge 5")
            )
        }

        val badgeList = remember {
            mutableStateListOf(
                BadgeInfo(6, R.drawable.badge_test_2, "badge 6"),
                BadgeInfo(7, R.drawable.badge_test_2, "badge 7"),
                BadgeInfo(8, R.drawable.badge_test_2, "badge 8"),
                BadgeInfo(9, R.drawable.badge_test_2, "badge 9"),
                BadgeInfo(10, R.drawable.badge_test_2, "badge 10"),
                BadgeInfo(11, R.drawable.badge_test_2, "badge 11"),
                BadgeInfo(11, R.drawable.badge_test_2, "badge 12"),
                BadgeInfo(11, R.drawable.badge_test_2, "badge 13"),
            )
        }

        fun changeBadge(mainBadge: BadgeInfo, badge: BadgeInfo) {
            val mainBadgeIndex = mainBadgeList.indexOf(mainBadge)
            val badgeIndex = badgeList.indexOf(badge)

            mainBadgeList[mainBadgeIndex] = badge
            badgeList[badgeIndex] = mainBadge
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                WalkieTitleBar(
                    title = stringResource(id = R.string.total_badge_title),
                    onBackClick = { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.setting_main_badge_title),
                    style = WalkieTypography.SubTitle
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.setting_main_badge_description),
                    style = WalkieTypography.Body2,
                    color = Color(0xFF808080)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        mainBadgeList.forEach {
                            MainBadgeItem(
                                badgeInfo = it,
                                currentState = dragTargetInfo,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    val badgeCountText = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color(0xFFF45A00),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(
                                stringResource(
                                    id = R.string.current_badge_count,
                                    mainBadgeList.size + badgeList.size
                                )
                            )
                        }
                        append(" / ")

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("16")
                        }

                        append(stringResource(id = R.string.total_badge_count))
                    }

                    Text(
                        text = badgeCountText,
                        style = WalkieTypography.Body2,
                        color = Color(0xFF9C9C9C)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val chunkedBadges = badgeList.chunked(4) // 뱃지를 4개씩 묶음

                    Column {
                        chunkedBadges.forEach { rowBadges ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowBadges.forEach { badge ->
                                    var currentInfo by remember { mutableStateOf(badge) }

                                    DropTarget<BadgeInfo>(
                                        dragInfo = dragTargetInfo,
                                        modifier = Modifier.wrapContentSize()
                                    ) {isInBound: Boolean, data: BadgeInfo? ->
                                        if (isInBound && !dragTargetInfo.isDragging) {
                                            if (dragTargetInfo.dataToDrop is BadgeInfo) {
                                                changeBadge(dragTargetInfo.dataToDrop as BadgeInfo, currentInfo)
                                                currentInfo = dragTargetInfo.dataToDrop as BadgeInfo
                                            }
                                        }
                                        BadgeItem(currentInfo)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTotalBadgePage() {
    WalkieTheme {
        TotalBadgeScreen(navController = rememberNavController())
    }
}