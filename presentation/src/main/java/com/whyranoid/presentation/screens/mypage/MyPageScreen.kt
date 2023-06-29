package com.whyranoid.presentation.screens.mypage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.presentation.reusable.TextWithCountSpaceBetween
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.UserPageState
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MyPageScreen(
    navController: NavController,
    uid: String,
) {
    val viewModel = koinViewModel<UserPageViewModel>()

    LaunchedEffect(true) {
        viewModel.getUserDetail(uid)
        viewModel.getUserBadges(uid)
        viewModel.getUserPostPreviews(uid)
    }

    val state by viewModel.collectAsState()

    MyPageContent(state, onPostPreviewClicked = {})
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageContent(
    state: UserPageState,
    onPostPreviewClicked: (id: Long) -> Unit = {}, // TODO 아이탬 클릭시 이벤트 처리
    onPostCreateClicked: () -> Unit = {}, // TODO 아이탬 생성 이벤트 처리
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp),
                text = "마이 페이지",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 28.dp),
        ) {
            state.userDetailState.getDataOrNull()?.let { userDetail ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = userDetail.user.imageUrl,
                        contentDescription = "유저 프로필 이미지",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .size(70.dp),
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // TODO: 스트링 리소스 분리
                        TextWithCountSpaceBetween(
                            text = "게시물",
                            count = userDetail.postCount,
                            textStyle = WalkieTypography.Body1_Normal,
                            countTextStyle = WalkieTypography.SubTitle,
                        )
                        TextWithCountSpaceBetween(
                            text = "팔로워",
                            count = userDetail.followerCount,
                            textStyle = WalkieTypography.Body1_Normal,
                            countTextStyle = WalkieTypography.SubTitle,
                        )
                        TextWithCountSpaceBetween(
                            text = "팔로잉",
                            count = userDetail.followingCount,
                            textStyle = WalkieTypography.Body1_Normal,
                            countTextStyle = WalkieTypography.SubTitle,
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            state.userBadgesState.getDataOrNull()?.let { userBadges ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                ) {
                    items(userBadges.size) { index ->
                        AsyncImage(
                            model = userBadges[index].imageUrl,
                            contentDescription = "badge image",
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clip(CircleShape)
                                .size(56.dp),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            val pagerList = listOf("게시물", "내기록", "챌린지")

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.height(40.dp),
                backgroundColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = WalkieColor.Primary,
                    )
                },
            ) {
                pagerList.forEachIndexed { index, pageName ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    ) {
                        Text(
                            text = pageName,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            HorizontalPager(
                pageCount = pagerList.size,
                state = pagerState,
                contentPadding = PaddingValues(top = 4.dp),
            ) { pagerNum ->
                when (pagerNum) {
                    0 -> state.userPostPreviewsState.getDataOrNull()?.let { postPreviews ->
                        PostPage(
                            postPreviews = postPreviews,
                            onPostPreviewClicked,
                            onPostCreateClicked,
                        )
                    }
                    1 -> HistoryPage()
                    2 -> ChallengePage()
                }
            }
        }
    }
}

@Composable
fun PostPage(
    postPreviews: List<PostPreview>,
    onPostPreviewClicked: (id: Long) -> Unit,
    onPostCreateClicked: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(postPreviews.size + 1) { index ->

            if (index < postPreviews.size) {
                AsyncImage(
                    model = postPreviews[index].imageUrl,
                    contentDescription = "postPreview Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp)).clickable {
                            onPostPreviewClicked(postPreviews[index].id)
                        },
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            width = 2.dp,
                            color = WalkieColor.GrayDefault,
                            shape = RectangleShape,
                        ).clickable {
                            onPostCreateClicked()
                        },
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center).size(22.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "포스트 생성",
                        tint = WalkieColor.GrayDefault,
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryPage() {
}

@Composable
fun ChallengePage() {
}
