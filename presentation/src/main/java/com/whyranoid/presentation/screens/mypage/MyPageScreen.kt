package com.whyranoid.presentation.screens.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.reusable.TextWithCountSpaceBetween
import com.whyranoid.presentation.screens.Screen
import com.whyranoid.presentation.screens.mypage.tabs.ChallengePage
import com.whyranoid.presentation.screens.mypage.tabs.HistoryPage
import com.whyranoid.presentation.screens.mypage.tabs.PostImagePreview
import com.whyranoid.presentation.screens.mypage.tabs.PostPage
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.UserPageState
import com.whyranoid.presentation.viewmodel.UserPageViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import java.time.LocalDate
import java.util.*

@Composable
fun MyPageScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<UserPageViewModel>()

    LaunchedEffect(Unit) {
        val myUid = requireNotNull(viewModel.accountRepository.uId.first())
        viewModel.getUserDetail(myUid, true)
        viewModel.getUserBadges(myUid)
        viewModel.getUserPostPreviews(myUid)
    }

    val state by viewModel.collectAsState()

    UserPageContent(
        nickname = null,
        state,
        onPostCreateClicked = {
            navController.navigate(Screen.AddPostScreen.route)
        },
        onProfileEditClicked = {
            navController.navigate(Screen.EditProfileScreen.route)
        },
        onLogoutClicked = {
            viewModel.signOut()
        },
        onDateClicked = viewModel::selectDate,
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPageContent(
    nickname: String? = null,
    state: UserPageState,
    onPostPreviewClicked: (id: Long) -> Unit = {},
    onPostCreateClicked: () -> Unit = {},
    onProfileEditClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onLogoutClicked: () -> Unit = {},
    onDateClicked: (LocalDate) -> Unit = {},
    onFollowButtonClicked: (uid: Long) -> Unit = {}, // TODO 상대방일 때 팔로잉 버튼 보이게
) {
    Scaffold(
        topBar = {
            nickname?.let { UserPageTopAppBar(it) } ?: run {
                MyPageTopAppBar(
                    onProfileEditClicked,
                    onSettingsClicked,
                    onLogoutClicked,
                )
            }
        },
    ) { paddingValues ->

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 28.dp)
                .verticalScroll(scrollState),
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
                Spacer(Modifier.height(12.dp))
            }

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
                                .clip(RoundedCornerShape(8.dp))
                                .size(56.dp),
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            val pagerList = listOf("게시물", "내기록", "챌린지")

            val pagerState = rememberPagerState {
                pagerList.size
            }

            val coroutineScope = rememberCoroutineScope()

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
                state = pagerState,
                contentPadding = PaddingValues(top = 4.dp),
                verticalAlignment = Alignment.Top,
            ) { pagerNum ->
                when (pagerNum) {
                    0 -> state.userPostPreviewsState.getDataOrNull()?.let { postPreviews ->
                        PostPage(
                            nickname == null,
                            postPreviews,
                            onPostPreviewClicked,
                            onPostCreateClicked,
                        )
                    }

                    1 -> {
                        Column {
                            HistoryPage(onDayClicked = onDateClicked)
                            state.calendarPreviewsState.getDataOrNull()
                                ?.let { postPreviews ->
                                    postPreviews.forEach { postPreview ->
                                        PostImagePreview(
                                            Modifier
                                                .fillMaxSize()
                                                .padding(40.dp),
                                            postPreview,
                                            onPostPreviewClicked,
                                        )
                                    }
                                }
                        }
                    }

                    2 -> ChallengePage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageTopAppBar(
    onProfileEditClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onLogoutClicked: () -> Unit = {},
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "마이 페이지",
                style = WalkieTypography.Title,
            )
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.Menu, "")
            }
            DropdownMenu(
                modifier = Modifier.width(180.dp),
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(onClick = { onProfileEditClicked() }) {
                    Text(text = "프로필 편집")
                }
                DropdownMenuItem(onClick = { onSettingsClicked() }) {
                    Text(text = "설정")
                }
                DropdownMenuItem(onClick = { onLogoutClicked() }) {
                    Text(text = "로그아웃")
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
    )
}

@Composable
fun UserPageTopAppBar(nickname: String) {
    WalkieTopBar(
        leftContent = {
            Row {
                Text(
                    text = nickname,
                    style = WalkieTypography.Title.copy(fontWeight = FontWeight(600)),
                )
            }
        },
    )
}
