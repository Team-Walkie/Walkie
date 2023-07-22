package com.whyranoid.data.repository

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.whyranoid.domain.model.running.UserLocation
import com.whyranoid.domain.repository.RunningRepository
import com.whyranoid.runningdata.RunningDataManager
import com.whyranoid.runningdata.model.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

class RunningRepositoryImpl(context: Context) : RunningRepository {

    private val runningDataManager = RunningDataManager.getInstance()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L).build()
    private lateinit var locationCallback: LocationCallback

    override val userLocationState: MutableStateFlow<UserLocation> =
        MutableStateFlow(UserLocation.NotTracking)

    override suspend fun startRunning() {
    }

    override suspend fun pauseRunning() {
        TODO("Not yet implemented")
    }

    override suspend fun resumeRunning() {
        TODO("Not yet implemented")
    }

    override suspend fun finishRunning() {
        TODO("Not yet implemented")
    }

    override fun listenLocation() {
        if (userLocationState.value is UserLocation.Tracking) return
        if ((runningDataManager.runningState.value is RunningState.NotRunning).not()) return
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    userLocationState.value =
                        UserLocation.Tracking(location.latitude, location.longitude)
                    Log.d("listenLocation", "listenLocation")
                } ?: run {
                    removeListener()
                }
            }
        }
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper(),
            )
        } catch (e: SecurityException) {
            removeListener()
        } catch (e: Exception) {
            removeListener()
        }
    }

    override fun removeListener() {
        userLocationState.value = UserLocation.NotTracking
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
