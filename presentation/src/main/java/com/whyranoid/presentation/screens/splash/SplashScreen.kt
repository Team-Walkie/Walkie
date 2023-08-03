package com.whyranoid.presentation.screens.splash

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whyranoid.presentation.theme.WalkieTheme

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    // val viewModel
    Text("splash")
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    WalkieTheme {
        SplashScreen()
    }
}
