package com.whyranoid.walkie.walkiedialog

sealed class DialogState {
    object Initialized : DialogState()

    object Valid : DialogState()

    data class InValid(val dialogContentProvider: DialogContentProvider) : DialogState()
}
