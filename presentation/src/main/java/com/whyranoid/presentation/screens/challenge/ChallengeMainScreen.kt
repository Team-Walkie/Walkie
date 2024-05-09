package com.whyranoid.presentation.screens.challenge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType
import com.whyranoid.presentation.component.ChallengeItem
import com.whyranoid.presentation.component.ChallengingItem
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.reusable.WalkieCircularProgressIndicator
import com.whyranoid.presentation.theme.ChallengeColor.getColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.util.chunkedList
import com.whyranoid.presentation.viewmodel.challenge.ChallengeMainState
import com.whyranoid.presentation.viewmodel.challenge.ChallengeMainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ChallengeMainScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<ChallengeMainViewModel>()

    val state by viewModel.collectAsState()

    ChallengeMainContent(
        state,
        onChallengeItemClicked = { challengePreview, isChallenging ->
            val route = "challengeDetail/${challengePreview.id}/$isChallenging"
            navController.navigate(route)
        },
        onExpandButtonClicked = {

        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChallengeMainContent(
    state: ChallengeMainState,
    onChallengeItemClicked: (ChallengePreview, Boolean) -> Unit = { _, _ -> },
    onExpandButtonClicked: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            WalkieTopBar(
                leftContent = {
                    Text(
                        text = "챌린지",
                        style = WalkieTypography.Title.copy(fontWeight = FontWeight(600)),
                    )
                },
                rightContent = {
                    Row {
                        Icon(
                            modifier = Modifier
                                .clickable {

                                },
                            imageVector = Icons.Filled.Search, contentDescription = "검색 버튼"
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            modifier = Modifier
                                .clickable {

                                },
                            imageVector = Icons.Filled.Menu, contentDescription = "메뉴 버튼"
                        )
                    }

                },
            )
        },
    ) { paddingValues ->
        val pagerState = rememberPagerState() {
            ChallengeType.values().size
        }


        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {

            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "신규",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {

                    state.newChallengePreviewsState.getDataOrNull()?.let { newChallengePreviews ->
                        newChallengePreviews.forEach {
                            item {
                                ChallengeItem(
                                    Modifier.fillParentMaxWidth(0.9f),
                                    it.type.getColor(),
                                    text = it.title
                                ) {
                                    onChallengeItemClicked(it, false)
                                }
                            }
                        }
                    } ?: run {
                        item {
                            WalkieCircularProgressIndicator(Modifier.fillParentMaxWidth())
                        }
                    }

                }
                Spacer(modifier = Modifier.height(44.dp))
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "도전중인 챌린지",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    state.challengingPreviewsState.getDataOrNull()?.let { challengingPreviews ->

                        if (challengingPreviews.isNotEmpty()) {
                            challengingPreviews.take(4).forEach {
                                ChallengingItem(
                                    text = it.title,
                                    progress = it.progress!!,
                                    challengeColor = it.type.getColor(),
                                ) {
                                    onChallengeItemClicked(it, true)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            IconButton(
                                onClick = { onExpandButtonClicked() }) {
                                Icon(
                                    imageVector = Icons.Rounded.ExpandMore,
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp),
                                    contentDescription = "도전중인 챌린지 더보기"
                                )
                            }
                        } else {

                        }


                    } ?: run {
                        WalkieCircularProgressIndicator(Modifier.fillParentMaxWidth())
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "다른 챌린지도 도전해보세요!",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "인기 챌린지",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    state.topRankChallengePreviewState.getDataOrNull()?.let { newChallengePreviews ->
                        newChallengePreviews.chunkedList(3).forEach { list ->
                            item {
                                Column() {
                                    list.forEach {
                                        ChallengeItem(
                                            Modifier.fillParentMaxWidth(0.9f),
                                            it.type.getColor(),
                                            text = it.title
                                        ) {
                                            onChallengeItemClicked(it, false)
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }

                    } ?: run {
                        item {
                            WalkieCircularProgressIndicator(Modifier.fillParentMaxWidth())
                        }
                    }

                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "유형별 챌린지",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {

                val coroutineScope = rememberCoroutineScope()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChallengeType.values().forEachIndexed { page, challengeType ->
                        Text(
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            },
                            text = challengeType.text,
                            style = WalkieTypography.SubTitle.copy(
                                color = if (page == pagerState.currentPage) Color.Black else Color.Gray
                            ),
                        )

                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.padding(horizontal = 20.dp),
                ) {

                    state.typedChallengePreviewsState.getDataOrNull()
                        ?.let { typedChallengePreviewsState ->
                            Column() {
                                typedChallengePreviewsState[pagerState.currentPage].forEach { challengePreview ->

                                    ChallengeItem(
                                        challengeColor = challengePreview.type.getColor(),
                                        text = challengePreview.title
                                    ) {
                                        onChallengeItemClicked(challengePreview, false)
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        } ?: run { WalkieCircularProgressIndicator(Modifier.fillParentMaxWidth()) }

                }
            }
        }

    }
}


