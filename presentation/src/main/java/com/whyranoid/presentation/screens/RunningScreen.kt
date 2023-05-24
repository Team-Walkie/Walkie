package com.whyranoid.presentation.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RunningScreen(
    navController: NavController
) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 10.0, minZoom = 5.0)
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isScaleBarEnabled = false,
                isZoomControlEnabled = false
            )
        )
    }

    NaverMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        properties = mapProperties,
        uiSettings = mapUiSettings,
    )

}