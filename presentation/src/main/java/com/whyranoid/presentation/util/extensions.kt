package com.whyranoid.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min
import kotlin.random.Random

fun <T> List<T>.chunkedList(size: Int): List<List<T>> {
    val newList = mutableListOf<List<T>>()
    var index = 0
    while (index < this.size) {
        val chunk = this.subList(index, min(index + size, this.size))
        newList.add(chunk)
        index += size
    }
    return newList
}

fun ClosedFloatingPointRange<Float>.random(): Float {
    return Random.nextDouble(start.toDouble(), endInclusive.toDouble()).toFloat()
}

fun Int.toRunningTime(): String {
    return "${"%02d".format(this.div(3600))}:${
        "%02d".format(
            this.rem(3600).div(60),
        )
    }:${"%02d".format(this.rem(60))}"
}

fun Double.toPace(): String {
    return "%.1f".format(this).replace('.', '`') + "``"
}

fun Int.dpToPx(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.pxToDp(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    val mul = when (scale) {
        1.0f -> 4.0f
        1.5f -> 8 / 3.0f
        2.0f -> 2.0f
        else -> 1.0f
    }
    return (this / (scale * mul)).toInt()
}

fun Activity.openSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null),
    ).also(::startActivity)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("WrongConstant")
fun Activity.openStatusBar() {
    try {
        val statusBarService = getSystemService(Context.STATUS_BAR_SERVICE)
        val statusBarManager = Class.forName("android.app.StatusBarManager")
        val expandStatusBar = statusBarManager.getMethod("expandNotificationsPanel")
        expandStatusBar.invoke(statusBarService)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}