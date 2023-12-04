package com.whyranoid.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.whyranoid.presentation.R
import com.whyranoid.presentation.theme.WalkieTheme

@Composable
fun SplashScreen(
    isDay: Boolean = true,
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = if (isDay) R.drawable.splash_day else R.drawable.splash_night),
            contentDescription = "splash screen",
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DaySplashScreenPreview() {
    WalkieTheme {
        SplashScreen(true)
    }
}

@Preview(showBackground = true)
@Composable
fun NightSplashScreenPreview() {
    WalkieTheme {
        SplashScreen(false)
    }
}
