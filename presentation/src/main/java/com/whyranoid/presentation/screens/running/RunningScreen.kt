package com.whyranoid.presentation.screens.running

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.whyranoid.presentation.R
import com.whyranoid.presentation.model.running.TrackingMode
import com.whyranoid.presentation.viewmodel.RunningScreenState
import com.whyranoid.presentation.viewmodel.RunningViewModel
import com.whyranoid.presentation.viewmodel.RunningViewModel.Companion.MAP_MAX_ZOOM
import com.whyranoid.presentation.viewmodel.RunningViewModel.Companion.MAP_MIN_ZOOM
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RunningScreen(
    navController: NavController,
    startWorker: () -> Unit,
) {
    val viewModel = koinViewModel<RunningViewModel>().apply { this.startWorker = startWorker }

    LaunchedEffect(true) {
        viewModel.getRunningState()
    }

    val state by viewModel.collectAsState()

    RunningContent(
        state,
        viewModel::startRunning,
        viewModel::onTrackingButtonClicked,
        viewModel::onTrackingCanceledByGesture,
    )
}

@Composable
fun RunningContent(
    state: RunningScreenState,
    onStartRunning: () -> Unit,
    onClickTrackingModeButton: () -> Unit,
    onTrackingCanceledByGesture: () -> Unit,
) {
    Box() {
        Column {
            RunningMapScreen(state, onClickTrackingModeButton, onTrackingCanceledByGesture)
            RunningInfoScreen(state = state)
        }
        RunningBottomButton(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onStartRunning = onStartRunning,
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RunningMapScreen(
    state: RunningScreenState,
    onClickTrackingModeButton: () -> Unit,
    onTrackingCanceledByGesture: () -> Unit,
) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = MAP_MAX_ZOOM,
                minZoom = MAP_MIN_ZOOM,
                locationTrackingMode =
                when (state.trackingModeState.getDataOrNull()) {
                    TrackingMode.FOLLOW -> LocationTrackingMode.Follow
                    TrackingMode.NO_FOLLOW -> LocationTrackingMode.NoFollow
                    else -> LocationTrackingMode.None
                },
            ),
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isScaleBarEnabled = false,
                isZoomControlEnabled = false,
                isCompassEnabled = false,
            ),
        )
    }

    // 카메라 위치 TODO 수정
    val cameraPositionState = rememberCameraPositionState()
        .apply {
            state.trackingModeState.getDataOrNull()?.let { trackingMode ->
                state.runningState.getDataOrNull()?.runningData?.lastLocation?.let { location ->
                    if (trackingMode == TrackingMode.FOLLOW) {
                        move(CameraUpdate.scrollTo(LatLng(location)))
                    }
                }
            }
        }

    Box(Modifier.wrapContentSize()) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = rememberCustomLocationSource(),
        ) {
            state.runningState.getDataOrNull()?.runningData?.lastLocation?.let { location ->
                LocationOverlay(
                    position = LatLng(location),
                    icon = OverlayImage.fromResource(R.drawable.ic_running_screen),
                )
            }

            DisposableMapEffect(true) { naverMap ->
                naverMap.addOnCameraChangeListener { reason, _ ->
                    if (reason == CameraUpdate.REASON_GESTURE) onTrackingCanceledByGesture()
                }

                onDispose {
                }
            }
        }
        Button(onClick = {
            state.runningState.getDataOrNull()?.runningData?.lastLocation?.let { location ->
                if (state.trackingModeState.getDataOrNull() == TrackingMode.NONE) {
                    cameraPositionState.move(
                        CameraUpdate.scrollTo(LatLng(location)),
                    )
                }
            }
            onClickTrackingModeButton()
        }) {
            Text("Tracking Mode")
        }
    }
}

@Composable
fun RunningInfoScreen(
    state: RunningScreenState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
    }
    state.runningState.getDataOrNull()?.let {
        it.runningData.lastLocation?.let { location ->
        }
    }
}

@Composable
fun RunningBottomButton(
    modifier: Modifier = Modifier,
    state: RunningScreenState,
    onStartRunning: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(onClick = onStartRunning) {
            Text("Start Running, CS: ${state.trackingModeState.getDataOrNull()}")
            // Log.d("RunningBottomButton tag", " ${state.trackingModeState.getDataOrNull()}") TODO REMOVE
        }
    }
}

@Composable
fun rememberCustomLocationSource(): LocationSource {
    return remember {
        object : LocationSource {

            var listener: LocationSource.OnLocationChangedListener? = null

            override fun activate(listener: LocationSource.OnLocationChangedListener) {
                this.listener = listener
            }

            override fun deactivate() {
                listener = null
            }
        }
    }
}
