package com.whyranoid.walkie.wlakiedialog

sealed class DialogState {
    object Initialized : DialogState()

    object Valid : DialogState()

    data class InValid(val dialogProvider: DialogProvider) : DialogState()
}
