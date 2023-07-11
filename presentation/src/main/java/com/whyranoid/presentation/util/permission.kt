package com.whyranoid.presentation.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showPermissionRequestDialog(context: Context) {
    MaterialAlertDialogBuilder(context)
        .setTitle("위치 권한 요청")
        .setMessage("모각런에서 달리기 위해서는 GPS 사용 권한이 필요해요")
        .setPositiveButton("허용할게요") { _, _ ->
            startActivity(
                context,
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName ?: "", null),
                ),
                null,
            )
        }
        .setNegativeButton("그건 싫어요") { _, _ ->
        }
        .show()
}

fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {
    if (permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it,
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        Log.d("test5", "권한이 이미 존재합니다.")
    }

    /** 권한이 없는 경우 **/
    else {
        launcher.launch(permissions)
        Log.d("test5", "권한을 요청하였습니다.")
    }
}
