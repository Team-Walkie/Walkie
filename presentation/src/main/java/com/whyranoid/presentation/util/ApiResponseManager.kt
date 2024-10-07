package com.whyranoid.presentation.util


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicInteger

object ApiResponseDialog {
    private val loadingCount = AtomicInteger(0)

    private val _isLaoding = MutableStateFlow(false)
    val isLoading get() = _isLaoding.asStateFlow()

    private val _isShowError = MutableStateFlow(false)

    val isShowError get() = _isShowError.asStateFlow()

    fun startLoading() {
        if (loadingCount.incrementAndGet() > 0) _isLaoding.value = true
    }

    fun finishLoad(isSuccessFul: Boolean) {
        if (loadingCount.decrementAndGet() == 0) _isLaoding.value = false
        if (isSuccessFul.not()) {
            _isLaoding.value = false
            _isShowError.value = true
        }
    }

    fun closeErrorDialog() {
        _isShowError.value = false
    }
}