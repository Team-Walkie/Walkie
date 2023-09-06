package com.whyranoid.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.whyranoid.presentation.component.community.PostItem
import com.whyranoid.presentation.component.community.RunningFollowerItem

@Composable
fun CommunityScreen(
    navController: NavController
) {

    LazyColumn {

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


