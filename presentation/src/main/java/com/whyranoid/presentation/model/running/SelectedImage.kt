package com.whyranoid.presentation.model.running

import android.net.Uri

sealed class SelectedImage {
    object None : SelectedImage()
    data class Selected(val uri: Uri) : SelectedImage()
}
