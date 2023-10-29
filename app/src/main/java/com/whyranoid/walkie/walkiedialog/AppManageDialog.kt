package com.whyranoid.walkie.walkiedialog

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.util.openSettings
import com.whyranoid.presentation.util.openStatusBar
import org.koin.androidx.compose.koinViewModel

/**
 * Provide dialog
 *
 * 권한, gps, 네트워크 상태에 따른 다이얼로그 보여주기
 *
 * 분리한 이유: ExperimentalPermissionsApi 로써 언제든 변화 가능하기 때문에 메인 비지니스 로직, UI 로직에 포함시키지 않고 따로 분리
 *
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppManageDialog() {
    val viewModel = koinViewModel<DialogViewModel>()
    val activity = LocalContext.current as Activity

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
    ) { isGranted ->
        viewModel.setPermission(Manifest.permission.ACCESS_FINE_LOCATION, isGranted)
    }

    val storagePermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    ) { isGranted ->
        viewModel.setPermission(Manifest.permission.READ_EXTERNAL_STORAGE, isGranted)
    }

    val locationDialogState = viewModel.locationPermissionDialogState.collectAsStateWithLifecycle()
    val storageDialogState = viewModel.storagePermissionDialogState.collectAsStateWithLifecycle()
    val gpsDialogState =
        viewModel.gpsDialogState.collectAsStateWithLifecycle(initialValue = DialogState.Initialized)
    val networkDialogState =
        viewModel.networkDialogState.collectAsStateWithLifecycle(initialValue = DialogState.Initialized)

    LaunchedEffect(
        locationDialogState.value,
        storageDialogState.value,
        gpsDialogState.value,
        networkDialogState.value,
    ) {
        if (locationPermissionState.status.isGranted.not() && locationPermissionState.status.shouldShowRationale.not()) {
            locationPermissionState.launchPermissionRequest()
        } else if (storagePermissionState.status.isGranted.not() && storagePermissionState.status.shouldShowRationale.not()) {
            storagePermissionState.launchPermissionRequest()
        }
    }

    if (locationPermissionState.status.isGranted.not() && locationPermissionState.status.shouldShowRationale) {
        PermissionDialog(
            dialog = DialogContentProvider.LocationPermission,
            onAction = { activity.openSettings() },
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        )
    } else if (storagePermissionState.status.isGranted.not() && storagePermissionState.status.shouldShowRationale) {
        PermissionDialog(
            dialog = DialogContentProvider.StoragePermission,
            onAction = { activity.openSettings() },
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        )
    } else if (gpsDialogState.value is DialogState.InValid) {
        PermissionDialog(
            dialog = DialogContentProvider.GPS,
            onAction = {
                activity.openStatusBar()
            },
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        )
    } else if (networkDialogState.value is DialogState.InValid) {
        PermissionDialog(
            dialog = DialogContentProvider.Network,
            onAction = {
                activity.openStatusBar()
            },
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        )
    }
}

@Composable
fun PermissionDialog(
    dialog: DialogContentProvider,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onAction,
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "동의",
                    style = WalkieTypography.SubTitle.copy(color = WalkieColor.Primary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            onAction()
                        }
                        .padding(bottom = 20.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
            }
        },
        title = { Text(dialog.title, style = WalkieTypography.SubTitle) },
        text = {
            Text(
                text = dialog.description,
                style = WalkieTypography.Body1_Normal,
            )
        },
        modifier = modifier,
    )
}
