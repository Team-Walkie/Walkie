package com.whyranoid.presentation.screens.challenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.domain.util.getToday
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.challenge.ChallengeImageSaveViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ChallengeImageSaveScreen(
    navController: NavController,
    challengeId: Long,
) {

    val viewModel = koinViewModel<ChallengeImageSaveViewModel>()
    val state by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getChallengeDetail(challengeId)
    }
    Scaffold(
        topBar = {
            Row {
                IconButton(
                    modifier = Modifier
                        .padding(vertical = 19.dp)
                        .padding(start = 16.dp),
                    onClick = { navController.popBackStack() }) {
                    Icon(
                        painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "back arrow"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier,
        ) {

            Spacer(modifier = Modifier.size(118.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center,
                ) {

                    Image(
                        modifier = Modifier
                            .size(300.dp)
                            .padding(20.dp),
                        painter = painterResource(id = R.drawable.bg_badge),
                        contentDescription = "Badge background"
                    )

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = state.challenge.getDataOrNull()?.badge?.imageUrl,
                            contentDescription = "challenge badge image"
                        )

                        Spacer(modifier = Modifier.size(13.dp))

                        Text(
                            text = state.challenge.getDataOrNull()?.name ?: String.EMPTY,
                            style = WalkieTypography.Title
                        )
                    }

                    Box(
                        modifier = Modifier.matchParentSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 30.dp, bottom = 30.dp),
                            text = getToday().split(" ").first().replace("-", "."),
                            style = WalkieTypography.Body2
                        )
                    }

                }
            }
        }
    }
}