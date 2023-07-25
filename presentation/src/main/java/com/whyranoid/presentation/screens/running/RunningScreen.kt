package com.whyranoid.presentation.screens.running

import android.location.Location
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.whyranoid.domain.model.running.UserLocation
import com.whyranoid.presentation.R
import com.whyranoid.presentation.model.running.SelectedImage
import com.whyranoid.presentation.model.running.TrackingMode
import com.whyranoid.presentation.reusable.GalleryGrid
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.util.toPace
import com.whyranoid.presentation.util.toRunningTime
import com.whyranoid.presentation.viewmodel.RunningScreenState
import com.whyranoid.presentation.viewmodel.RunningViewModel
import com.whyranoid.presentation.viewmodel.RunningViewModel.Companion.MAP_MAX_ZOOM
import com.whyranoid.presentation.viewmodel.RunningViewModel.Companion.MAP_MIN_ZOOM
import com.whyranoid.runningdata.model.RunningState
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RunningScreen(
    navController: NavController,
    startWorker: () -> Unit,
) {
    val viewModel = koinViewModel<RunningViewModel>()

    LaunchedEffect(LocalLifecycleOwner.current) {
        viewModel.startWorker = startWorker
        viewModel.getRunningState()
        viewModel.onTrackingButtonClicked()
    }

    val state by viewModel.collectAsState()

    RunningContent(
        state,
        viewModel::startRunning,
        viewModel::onTrackingButtonClicked,
        viewModel::onTrackingCanceledByGesture,
        viewModel::pauseRunning,
        viewModel::resumeRunning,
        viewModel::finishRunning,
        viewModel::openEdit,
        viewModel::closeEdit,
        viewModel::selectImage,
    )
}

@Composable
fun RunningContent(
    state: RunningScreenState,
    onStartRunning: () -> Unit,
    onClickTrackingModeButton: () -> Unit,
    onTrackingCanceledByGesture: () -> Unit,
    onPauseRunning: () -> Unit,
    onResumeRunning: () -> Unit,
    onFinishRunning: () -> Unit,
    onEditOpen: () -> Unit,
    onEditClose: () -> Unit,
    selectImage: (Uri) -> Unit,
) {
    Box {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            RunningMapScreen(
                modifier = Modifier.weight(1f, false),
                state,
                onClickTrackingModeButton,
                onTrackingCanceledByGesture,
                onEditOpen,
                onEditClose,
            )
            RunningInfoScreen(
                modifier = Modifier.height(280.dp),
                state = state,
                onSelectImage = selectImage,
            )
        }
        RunningBottomButton(
            modifier = Modifier
                .height(50.dp)
                .width(80.dp),
            state = state,
            onStartRunning = onStartRunning,
            onPauseRunning = onPauseRunning,
            onResumeRunning = onResumeRunning,
            onFinishRunning = onFinishRunning,
            onEditClose = onEditClose,
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun RunningMapScreen(
    modifier: Modifier = Modifier,
    state: RunningScreenState,
    onClickTrackingModeButton: () -> Unit,
    onTrackingCanceledByGesture: () -> Unit,
    onEditOpen: () -> Unit,
    onEditClose: () -> Unit,
) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = MAP_MAX_ZOOM,
                minZoom = MAP_MIN_ZOOM,
                locationTrackingMode = when (state.trackingModeState.getDataOrNull()) {
                    TrackingMode.FOLLOW -> LocationTrackingMode.Follow
                    TrackingMode.NO_FOLLOW -> LocationTrackingMode.NoFollow
                    else -> LocationTrackingMode.Follow
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
                isLogoClickEnabled = false,
                logoMargin = PaddingValues(bottom = 44.dp, start = 18.dp),
            ),
        )
    }

    val cameraPositionState = rememberCameraPositionState().apply {
        state.trackingModeState.getDataOrNull()?.let { trackingMode ->
            state.runningState.getDataOrNull()?.runningData?.lastLocation?.let { location ->
                if (trackingMode == TrackingMode.FOLLOW) {
                    move(CameraUpdate.scrollTo(LatLng(location)))
                }
            }
        }
    }

    // TODO 2. 종료시 화면 고정(모각런 참고), 6. 저장하기 구현, 7. 갤러리 아래 패딩 및 버튼 변경
    Box(modifier) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = modifier,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = rememberCustomLocationSource(),
        ) {
            if (cameraPositionState.cameraUpdateReason.value == CameraUpdate.REASON_GESTURE) onTrackingCanceledByGesture()

            state.userLocationState.getDataOrNull()?.let {
                if (it is UserLocation.Tracking) {
                    val location = Location("").apply {
                        latitude = it.lat
                        longitude = it.lng
                    }
                    LaunchedEffect(Unit) {
                        cameraPositionState.move(
                            CameraUpdate.scrollTo(LatLng(location)),
                        )
                    }
                    LocationOverlay(
                        position = LatLng(location),
                        icon = OverlayImage.fromResource(R.drawable.ic_running_screen_selected),
                    )
                }
            }

            state.runningState.getDataOrNull()?.let {
                it.runningData.lastLocation?.let { location ->
                    LocationOverlay(
                        position = LatLng(location),
                        icon = OverlayImage.fromResource(R.drawable.ic_running_screen_selected),
                    )
                }

                it.runningData.runningPositionList.forEach { path ->
                    if (path.size > 2) {
                        PathOverlay(
                            coords = path.map { position ->
                                LatLng(
                                    position.latitude,
                                    position.longitude,
                                )
                            },
                            color = WalkieColor.Primary,
                            width = 12.dp,
                            outlineWidth = 0.dp,
                        )
                    }
                }
            }
        }
        // 편집 중
        state.editState.getDataOrNull()?.let { editState ->
            // 탑 앱바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    Icons.Default.Close,
                    "close",
                    modifier = Modifier.clickable { onEditClose() },
                )
                Text("이미지", style = WalkieTypography.SubTitle)
                Icon(
                    Icons.Outlined.PhotoCamera,
                    "camera",
                    modifier = Modifier.clickable {
                        /* TODO */
                    },
                )
            }
        }

        // 러닝 종료 시
        state.runningFinishState.getDataOrNull()?.let { finData ->
            state.editState.getDataOrNull() ?: kotlin.run {
                Text(
                    text = "편집",
                    style = WalkieTypography.SubTitle,
                    modifier = Modifier.clickable { onEditOpen() }.padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White).padding(8.dp)
                        .align(Alignment.TopEnd),
                )
            }

            // 지도 하단 정보
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                listOf(
                    "%.2f".format(finData.runningHistory.totalDistance.div(1000.toDouble())),
                    finData.runningHistory.totalRunningTime.toRunningTime(),
                    finData.runningHistory.pace.toPace(),
                ).forEach {
                    Text(
                        it,
                        style = WalkieTypography.Title,
                    )
                }
            }
        } ?: run {
            // 러닝 중
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { /* TODO */ }
                        .background(Color.White)
                        .height(24.dp)
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(4.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "",
                        tint = WalkieColor.Primary,
                    )
                    Text(
                        text = "0",
                        style = WalkieTypography.Body1,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                }

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            state.runningState.getDataOrNull()?.runningData?.lastLocation?.let { location ->
                                if (state.trackingModeState.getDataOrNull() == TrackingMode.NONE) {
                                    cameraPositionState.move(
                                        CameraUpdate.scrollTo(LatLng(location)),
                                    )
                                }
                            }
                            onClickTrackingModeButton()
                        }
                        .background(Color.White)
                        .size(24.dp)
                        .padding(4.dp),
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "",
                    tint = WalkieColor.Primary,
                )
            }
        }
    }
}

@Composable
fun RunningInfoScreen(
    state: RunningScreenState,
    modifier: Modifier = Modifier,
    onSelectImage: (Uri) -> Unit,
) {
    state.editState.getDataOrNull()?.let { _ ->
        GalleryGrid(
            column = 3,
            modifier = modifier
                .background(Color.White),
        ) { uri -> onSelectImage(uri) }
        return
    }
    Column(
        modifier = modifier
            .padding(bottom = 66.dp)
            .background(Color.White),
    ) {
        // 거리, 런닝 시간
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 20.dp),
        ) {
            Column(
                Modifier
                    .wrapContentHeight()
                    .weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "거리",
                    style = WalkieTypography.Caption,
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "%.2f".format(
                            state.runningResultInfoState.getDataOrNull()?.distance?.div(1000.toDouble())
                                ?: state.runningInfoState.getDataOrNull()?.distance?.div(1000.toDouble())
                                ?: 0.00,
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = "km",
                        style = WalkieTypography.Body2,
                    )
                }
            }

            Column(
                Modifier
                    .wrapContentHeight()
                    .weight(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "런닝시간",
                    style = WalkieTypography.Caption,
                )
                Text(
                    text = state.runningResultInfoState.getDataOrNull()?.runningTime?.toRunningTime()
                        ?: state.runningInfoState.getDataOrNull()?.runningTime?.toRunningTime()
                        ?: "00:00:00",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
            }
        }

        // 페이스 칼로리 걸음수
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Column(
                Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "페이스",
                    style = WalkieTypography.Caption,
                )
                Text(
                    text = state.runningResultInfoState.getDataOrNull()?.pace?.toPace()
                        ?: state.runningInfoState.getDataOrNull()?.pace?.toPace() ?: "0`00``",
                    style = WalkieTypography.SubTitle,
                )
            }

            Column(
                Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "칼로리",
                    style = WalkieTypography.Caption,
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${state.runningResultInfoState.getDataOrNull()?.calories?.toInt() ?: state.runningInfoState.getDataOrNull()?.calories?.toInt() ?: 0}",
                        style = WalkieTypography.SubTitle,
                    )
                    Text(
                        text = "kcal",
                        style = WalkieTypography.SubTitle,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

            Column(
                Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "걸음수",
                    style = WalkieTypography.Caption,
                )
                Text(
                    text = state.runningResultInfoState.getDataOrNull()?.steps?.let { it.toString() }
                        ?: state.runningInfoState.getDataOrNull()?.steps?.let { it.toString() }
                        ?: "0",
                    style = WalkieTypography.SubTitle,
                )
            }
        }
    }
}

@Composable
fun RunningBottomButton(
    modifier: Modifier = Modifier,
    state: RunningScreenState,
    onStartRunning: () -> Unit,
    onPauseRunning: () -> Unit,
    onResumeRunning: () -> Unit,
    onFinishRunning: () -> Unit,
    onEditClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // 편집 중
        state.editState.getDataOrNull()?.let { editState ->
            if (editState is SelectedImage.Selected) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = { onEditClose() },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        "확인",
                        style = WalkieTypography.Title,
                    )
                }
            }
        } ?: state.runningFinishState.getDataOrNull()?.let { finState -> // 종료 시
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(modifier = Modifier.padding(bottom = 8.dp), text = "런닝을 종료했습니다.")
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        /* TODO 저장하기 */
                    },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        "저장",
                        style = WalkieTypography.Title,
                    )
                }
            }
        } ?: state.runningState.getDataOrNull()?.let { runningState -> // 러닝 시작 전
            when (runningState) {
                is RunningState.NotRunning -> {
                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .width(160.dp),
                        onClick = onStartRunning,
                    ) {
                        Text("러닝 시작", style = WalkieTypography.Title)
                    }
                }
                is RunningState.Paused -> {
                    Row(
                        Modifier
                            .wrapContentSize()
                            .border(
                                width = 1.dp,
                                color = WalkieColor.GrayDefault,
                                shape = CircleShape,
                            )
                            .clip(CircleShape)
                            .background(Color.White),
                    ) {
                        IconButton(modifier = modifier, onClick = { onResumeRunning() }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "")
                        }
                        IconButton(modifier = modifier, onClick = { onFinishRunning() }) {
                            Icon(
                                Icons.Default.Stop,
                                contentDescription = "",
                                tint = WalkieColor.Primary,
                            )
                        }
                    }
                }
                is RunningState.Running -> {
                    Row(
                        Modifier
                            .wrapContentSize()
                            .border(
                                width = 1.dp,
                                color = WalkieColor.GrayDefault,
                                shape = CircleShape,
                            )
                            .clip(CircleShape)
                            .background(Color.White),
                    ) {
                        IconButton(modifier = modifier, onClick = { onPauseRunning() }) {
                            Icon(Icons.Default.Pause, contentDescription = "")
                        }
                        IconButton(modifier = modifier, onClick = { onFinishRunning() }) {
                            Icon(
                                Icons.Default.Stop,
                                contentDescription = "",
                                tint = WalkieColor.Primary,
                            )
                        }
                    }
                }
            }
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
