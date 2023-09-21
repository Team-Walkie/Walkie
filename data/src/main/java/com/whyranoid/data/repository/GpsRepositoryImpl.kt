package com.whyranoid.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import com.whyranoid.domain.repository.GpsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GpsRepositoryImpl(private val context: Context) : GpsRepository {

    private val _gpsEnabledState = MutableStateFlow(isGpsEnabled())
    override fun getGpsEnabledState() = _gpsEnabledState.asStateFlow()

    private val gpsStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            checkGpsStatus()
        }
    }

    override fun registerReceiver() {
        context.registerReceiver(
            gpsStatusReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION),
        )
    }

    override fun unRegisterReceiver() = context.unregisterReceiver(gpsStatusReceiver)

    private fun checkGpsStatus() {
        _gpsEnabledState.value = isGpsEnabled()
    }

    private fun isGpsEnabled() = context.getSystemService(LocationManager::class.java)
        .isProviderEnabled(LocationManager.GPS_PROVIDER)
}
