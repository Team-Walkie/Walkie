package com.whyranoid.presentation.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.domain.model.user.User
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SignInInitialScreen(
    modifier: Modifier = Modifier,
    onSuccessAuth: (authId: String, userName: String, imageUrl: String?) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(112.dp))
        Text(
            "Let's Walkie",
            style = WalkieTypography.Title,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 112.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.walkie_splash_logo),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 292.dp),
        )

        Text(
            "SNS로 간편 가입",
            style = WalkieTypography.Body1_Normal,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 232.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 160.dp),
        ) {
            Surface(
                // TODO 실제 Auth 인증 구현
                modifier = Modifier.clickable {
                    onSuccessAuth(
                        "authId",
                        User.DUMMY.name,
                        User.DUMMY.imageUrl,
                    )
                },
                elevation = 2.dp,
                shape = CircleShape,
                color = Color(0xFFFEE500),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_kakao_logo),
                    contentDescription = "login with kakao",
                    modifier = Modifier.size(50.dp).padding(13.dp),
                    tint = Color.Black,
                )
            }
            Surface(
                // TODO 실제 Auth 인증 구현
                modifier = Modifier.clickable {
                    onSuccessAuth(
                        "authID",
                        User.DUMMY.name,
                        User.DUMMY.imageUrl,
                    )
                },
                elevation = 2.dp,
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_naver_logo),
                    contentDescription = "login with naver",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp),
                )
            }
            Surface(
                // TODO 실제 Auth 인증 구현
                modifier = Modifier.clickable {
                    onSuccessAuth(
                        "authId",
                        User.DUMMY.name,
                        User.DUMMY.imageUrl,
                    )
                },
                elevation = 2.dp,
                shape = CircleShape,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "login with google",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp).padding(13.dp),
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("이미 계정이 있으신가요?")
            Text("로그인하기", color = Color.Red, style = WalkieTypography.Body1_SemiBold)
        }
    }
}

@Preview
@Composable
fun PreviewSignInInitialScreen() {
    WalkieTheme {
        SignInInitialScreen(onSuccessAuth = { _, _, _ -> })
    }
}
