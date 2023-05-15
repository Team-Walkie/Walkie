package com.whyranoid.presentation.screens.challenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.whyranoid.presentation.R
import com.whyranoid.presentation.component.ChallengeGoalContent
import com.whyranoid.presentation.component.UserIcon
import com.whyranoid.presentation.component.button.WalkiePositiveButton
import com.whyranoid.presentation.viewmodel.ChallengeDetailState
import com.whyranoid.presentation.viewmodel.ChallengeDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ChallengeDetailScreen(
    navController: NavController,
    challengeId: Long
) {

    val viewModel = koinViewModel<ChallengeDetailViewModel>()

    LaunchedEffect(true) {
        viewModel.getChallengeDetail(challengeId)
    }

    val state by viewModel.collectAsState()

    ChallengeDetailContent(state)
}

@Composable
fun ChallengeDetailContent(
    state: ChallengeDetailState,
) {

    Scaffold() { paddingValues ->

        val scrollState = rememberScrollState()

        state.challenge.getDataOrNull()?.let { challenge ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
            ) {
                // TODO: Async Image
                Image(
                    painter = painterResource(id = R.drawable.dummy_challenge_banner),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                ) {

                    Text(
                        text = challenge.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = challenge.contents,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "도전 내용",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ChallengeGoalContent(challenge)

                    Spacer(modifier = Modifier.height(42.dp))

                    Text(
                        text = "성공 시 달성 뱃지",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = challenge.badge.imageUrl, contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = challenge.badge.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "함께 도전하는 워키들",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${challenge.participantCount}명",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow {
                        challenge.participants.forEach { participant ->
                            item {
                                UserIcon(user = participant)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    WalkiePositiveButton(text = "도전하기")

                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }


}


