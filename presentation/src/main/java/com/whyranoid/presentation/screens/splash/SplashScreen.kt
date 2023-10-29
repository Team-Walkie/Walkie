package com.whyranoid.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(112.dp))
        Text(
            "Let's Walkie",
            style = WalkieTypography.Title,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 112.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.walkie_splash_logo),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 292.dp),
        )

        Text(
            "스플래시 화면입니다~~",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
        )
    }
}

// @Composable
// fun PermissionDialog(
//    permission: DialogProvider,
//    isPermanentlyDeclined: Boolean,
//    onDismiss: () -> Unit,
//    onOkClick: () -> Unit,
//    onSettingClick: () -> Unit,
//    modifier: Modifier = Modifier,
// ) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        buttons = {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = if (isPermanentlyDeclined) "설정" else "동의",
//                    style = WalkieTypography.SubTitle.copy(color = WalkieColor.Primary),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.align(Alignment.End).clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() },
//                    ) {
//                        if (isPermanentlyDeclined) {
//                            onSettingClick()
//                        } else {
//                            onOkClick()
//                        }
//                    }.padding(bottom = 20.dp).padding(horizontal = 20.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                )
//            }
//        },
//        title = { Text(permission.title, style = WalkieTypography.SubTitle) },
//        text = {
//            Text(
//                text = permission.description,
//                style = WalkieTypography.Body1_Normal,
//            )
//        },
//        modifier = modifier,
//    )
// }

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    WalkieTheme {
        SplashScreen()
    }
}
