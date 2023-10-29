package com.whyranoid.walkie.wlakiedialog

import com.whyranoid.walkie.walkiedialog.DialogContentProvider

sealed class DialogState {
    object Initialized : DialogState()

    object Valid : DialogState()

    data class InValid(val dialogContentProvider: DialogContentProvider) : DialogState()
}
