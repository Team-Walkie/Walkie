package com.whyranoid.presentation.screens.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.presentation.component.badge.PlaceholderBadge
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.reusable.TextWithCountSpaceBetween
import com.whyranoid.presentation.screens.Screen
import com.whyranoid.presentation.screens.mypage.following.FollowingViewModel
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
    var uid by rememberSaveable { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        val myUid = requireNotNull(viewModel.accountRepository.uId.first())
        uid = myUid
        viewModel.getUserDetail(myUid, null)
        viewModel.getUserBadges(myUid)
        viewModel.getUserPostPreviews(myUid)
        viewModel.getChallengingPreviews(myUid)
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
        onFollowerCountClicked = {
            uid?.let {
                navController.navigate(
                    Screen.FollowingScreen.route(
                        it,
                        FollowingViewModel.FOLLOWER_PAGE_NO,
                    ),
                )
            }
        },
        onFollowingCountClicked = {
            uid?.let {
                navController.navigate(
                    Screen.FollowingScreen.route(
                        it,
                        FollowingViewModel.FOLLOWING_PAGE_NO,
                    ),
                )
            }
        },
        onChallengePreviewClicked = { challengeId: Long ->
            val route = "challengeDetail/$challengeId/true"
            navController.navigate(route)
        },
        goChallengeMainScreen = {
            navController.navigate(Screen.ChallengeMainScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
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
    onFollowButtonClicked: () -> Unit = {},
    onUnFollowButtonClicked: () -> Unit = {},
    onFollowerCountClicked: () -> Unit = {},
    onFollowingCountClicked: () -> Unit = {},
    onChallengePreviewClicked: (challengeId: Long) -> Unit = {},
    goChallengeMainScreen: () -> Unit = {},
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

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextWithCountSpaceBetween(
                                text = "게시물",
                                count = userDetail.postCount,
                                textStyle = WalkieTypography.Body1_Normal,
                                countTextStyle = WalkieTypography.SubTitle,
                            )
                            TextWithCountSpaceBetween(
                                modifier = Modifier.clickable {
                                    onFollowerCountClicked()
                                },
                                text = "팔로워",
                                count = userDetail.followerCount,
                                textStyle = WalkieTypography.Body1_Normal,
                                countTextStyle = WalkieTypography.SubTitle,
                            )
                            TextWithCountSpaceBetween(
                                modifier = Modifier.clickable {
                                    onFollowingCountClicked()
                                },
                                text = "팔로잉",
                                count = userDetail.followingCount,
                                textStyle = WalkieTypography.Body1_Normal,
                                countTextStyle = WalkieTypography.SubTitle,
                            )
                        }
                        nickname?.let {
                            val isFollowing = state.userDetailState.getDataOrNull()?.isFollowing
                            val followingButtonBackground =
                                if (isFollowing == false) WalkieColor.Primary else Color.White
                            val followButtonText =
                                if (isFollowing == true) "팔로잉" else if (isFollowing == false) "팔로우" else String.EMPTY
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(horizontal = 28.dp)
                                    .padding(vertical = 12.dp)
                                    .clip(
                                        RoundedCornerShape(10.dp),
                                    )
                                    .border(
                                        1.dp,
                                        WalkieColor.GrayBorder,
                                        RoundedCornerShape(10.dp),
                                    )
                                    .background(followingButtonBackground)
                                    .clickable {
                                        if (isFollowing == true) {
                                            onUnFollowButtonClicked()
                                        } else if (isFollowing == false) {
                                            onFollowButtonClicked()
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    text = followButtonText,
                                    style = WalkieTypography.Body1_SemiBold,
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            val badgeList = state.userBadgesState.getDataOrNull() ?: emptyList()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(WalkieColor.GrayBackground)
                    .padding(12.dp)
            ) {
                repeat(minOf(badgeList.size, 5)) {
                    AsyncImage(
                        model = badgeList[it].imageUrl,
                        contentDescription = "badge image",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .size(56.dp),
                    )
                }
                repeat(5 - badgeList.size) { PlaceholderBadge() }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = badgeList.size >= 5) {
                        // TODO 전체 뱃지 페이지로 이동
                    }
                    .background(WalkieColor.GrayBackground)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "전체 뱃지 보기" + if (badgeList.size < 5) "(${badgeList.size}/5)" else "",
                    fontSize = 14.sp,
                    color = if (badgeList.size < 5) WalkieColor.GrayBorder else Color.Black
                )
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

                    2 -> ChallengePage(
                        state.challengingPreviewsState.getDataOrNull() ?: emptyList(),
                        onChallengePreviewClicked,
                        goChallengeMainScreen
                    )
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
