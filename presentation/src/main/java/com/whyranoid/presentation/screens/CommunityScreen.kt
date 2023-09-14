package com.whyranoid.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whyranoid.presentation.component.bar.WalkieTopBar
import com.whyranoid.presentation.component.community.PostItem
import com.whyranoid.presentation.reusable.RunningFollowerItem
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun CommunityScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            WalkieTopBar(
                leftContent = {
                    Row {
                        Text(
                            text = "커뮤니티",
                            style = WalkieTypography.Title.copy(fontWeight = FontWeight(600)),
                        )

                        Spacer(modifier = Modifier.width(7.dp))

                        Icon(
                            modifier = Modifier
                                .clickable {
                                },
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Down Arrow",
                        )
                    }
                },
                rightContent = {
                    Row {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                },
                            imageVector = Icons.Filled.Add,
                            contentDescription = "추가 버튼",
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            modifier = Modifier
                                .clickable {
                                },
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색 버튼",
                        )
                    }
                },
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            item {
                LazyRow {
                    repeat(10) {
                        item { RunningFollowerItem() }
                    }
                }
            }

            repeat(10) {
                item {
                    PostItem()
                }
            }
        }
    }
}
