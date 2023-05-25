package com.whyranoid.walkie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.whyranoid.presentation.screens.AppScreen
import com.whyranoid.presentation.theme.WalkieTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalkieTheme {
                AppScreen()
            }
        }
    }
}
