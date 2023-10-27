package com.whyranoid.walkie

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.whyranoid.presentation.screens.AppScreen
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.walkie.wlakiedialog.AppManageDialog

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalkieTheme {
                AppManageDialog()
                AppScreen { startWorker(this) }
            }
        }
    }
}
