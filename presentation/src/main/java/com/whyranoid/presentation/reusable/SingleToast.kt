package com.whyranoid.presentation.reusable

import android.content.Context
import android.widget.Toast

object SingleToast {

    private const val DEFAULT_DURATION = 2_000L
    private var lastToastInfo: Pair<String, Long>? = null

    fun show(
        context: Context,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        if (lastToastInfo?.first != message) {
            lastToastInfo = message to System.currentTimeMillis()
            Toast.makeText(context, message, duration).show()
        } else {
            lastToastInfo?.second?.let {lastToastTime ->
                if (System.currentTimeMillis() - lastToastTime > DEFAULT_DURATION) {
                    lastToastInfo = message to System.currentTimeMillis()
                    Toast.makeText(context, message, duration).show()
                }
            }
        }
    }
}