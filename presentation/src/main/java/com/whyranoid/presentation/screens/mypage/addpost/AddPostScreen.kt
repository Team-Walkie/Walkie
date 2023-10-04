package com.whyranoid.presentation.screens.mypage.addpost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.whyranoid.domain.model.running.RunningHistory

@Composable
fun AddPostScreen(navController: NavHostController) {
    var selectedHistory by remember { mutableStateOf<RunningHistory?>(null) }

    selectedHistory?.let { runningHistory ->
        PostingScreen(runningHistory) { navController.navigateUp() }
    } ?: run {
        SelectHistoryScreen { runningHistory ->
            selectedHistory = runningHistory
        }
    }
}
