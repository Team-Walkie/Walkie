package com.whyranoid.presentation.screens.mypage.following

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.screens.community.SearchedFriendItem
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(
    navController: NavHostController,
    uid: Long,
    pageNo: Int,
) {
    val viewModel = koinViewModel<FollowingViewModel>()

    val followingSearchText = viewModel.followingSearchText.collectAsStateWithLifecycle()
    val followerSearchText = viewModel.followerSearchText.collectAsStateWithLifecycle()

    val followingList = viewModel.followingList.collectAsStateWithLifecycle()
    val followerList = viewModel.followerList.collectAsStateWithLifecycle()

    val isMyList = viewModel.isMyList.collectAsStateWithLifecycle()
    val removedFollowerList = viewModel.removedFollowerList.collectAsStateWithLifecycle()

    val pagerList = listOf("팔로워", "팔로잉")

    val pagerState = rememberPagerState {
        pagerList.size
    }

    LaunchedEffect(pageNo) {
        viewModel.loadList(uid)
        pagerState.scrollToPage(pageNo)
    }

    val coroutineScope = rememberCoroutineScope()

    Column {
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
                    androidx.compose.material3.Text(
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
                0 -> {
                    SearchFriendList(
                        followerSearchText.value,
                        followerList.value,
                        onTextChanged = {
                            viewModel.setFollowerSearchText(it)
                        },
                        onItemClicked = { user ->
                            navController.navigate("userPage/${user.uid}/${user.nickname}/${false}") // todo false -> null
                        },
                        actionButton = { user ->
                            if (isMyList.value) {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.removeFollower(user.uid)
                                        }
                                        .wrapContentSize()
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(WalkieColor.GrayDefault),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = "삭제")
                                }
                            }
                        },
                        removedFollowerList = removedFollowerList.value,
                    )
                }

                1 -> {
                    SearchFriendList(
                        followingSearchText.value,
                        followingList.value,
                        onTextChanged = {
                            viewModel.setFollowingSearchText(it)
                        },
                        onItemClicked = { user ->
                            navController.navigate("userPage/${user.uid}/${user.nickname}/${true}")
                        },
                        actionButton = { user ->

                            var isFollowing by remember { mutableStateOf(true) }

                            Row(
                                modifier = Modifier
                                    .clickable {
                                        if (isFollowing) {
                                            viewModel.unfollow(user.uid)
                                        } else {
                                            viewModel.follow(user.uid)
                                        }
                                        isFollowing = isFollowing.not()
                                    }
                                    .wrapContentSize()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(WalkieColor.GrayDefault),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(text = if (isFollowing) "팔로잉" else "팔로우")
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SearchFriendList(
    text: String,
    list: List<User>,
    onTextChanged: (text: String) -> Unit = {},
    onItemClicked: (user: User) -> Unit = {},
    actionButton: @Composable (user: User) -> Unit = {},
    removedFollowerList: List<Long>? = null,
) {
    Column {
        Box(
            modifier =
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(34.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE4E4E4),
                    shape = RoundedCornerShape(10.dp),
                )
                .clip(shape = RoundedCornerShape(10.dp)),
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 11.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0x80000000),
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    value = text,
                    onValueChange = { changedText: String ->
                        onTextChanged(changedText)
                    },
                    cursorBrush = SolidColor(WalkieColor.Primary),
                    singleLine = true,
                ) { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = "워키 아이디로 친구찾기",
                            style = WalkieTypography.Body1_Normal.copy(
                                color = Color(0x80000000),
                            ),
                        )
                    } else {
                        Text(
                            text = text,
                            style = WalkieTypography.Body1_Normal,
                        )
                    }
                    innerTextField()
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(count = list.size) { index ->
                val item = list[index]
                SearchedFriendItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    user = item,
                    onClickItem = { user ->
                        onItemClicked(user)
                    },
                    actionButton = if (removedFollowerList?.contains(item.uid)
                            ?.not() == true
                    ) {
                        actionButton
                    } else {
                        { _ -> }
                    },
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
