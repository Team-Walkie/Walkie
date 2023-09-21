package com.whyranoid.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface GpsRepository {
    fun getGpsEnabledState(): StateFlow<Boolean>
    fun registerReceiver()
    fun unRegisterReceiver()
}
