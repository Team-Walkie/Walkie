package com.whyranoid.presentation.screens.community

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.SearchFriendViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchFriendScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<SearchFriendViewModel>()

    Scaffold(
        topBar = {
            WalkieTopBar(
                leftContent = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            },
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cancel"
                    )
                },
                middleContent = {
                    Text(
                        text = "친구찾기",
                        style = WalkieTypography.Title.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600)
                        ),
                    )
                },
                rightContent = {
                    Spacer(modifier = Modifier.width(Icons.Filled.Close.defaultWidth))
                },
            )
        },
    ) {

        var query by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Box(
                modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(34.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE4E4E4),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 11.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = "Search Icon",
                        tint = Color(0x80000000)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        value = query,
                        onValueChange = { changedText ->
                            query = changedText
                        },
                        cursorBrush = SolidColor(WalkieColor.Primary),
                        singleLine = true,
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = "워키 아이디로 친구찾기",
                                style = WalkieTypography.Body1_Normal.copy(
                                    color = Color(0x80000000)
                                ),
                            )
                        } else {
                            Text(
                                text = query,
                                style = WalkieTypography.Body1_Normal
                            )
                        }

                    }
                }


            }

            Spacer(modifier = Modifier.height(20.dp))

            val pager = remember {
                viewModel.searchUsers(String.EMPTY)
            }

            val lazyPagingItems = pager.collectAsLazyPagingItems()

            LazyColumn {
                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Text(
                            text = "Waiting for items to load from the backend",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                items(count = lazyPagingItems.itemCount) { index ->

                    val item = lazyPagingItems[index]
                    SearchedFriendItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        nickname = item?.nickname ?: ""
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

                if (lazyPagingItems.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

            }

        }

    }
}
