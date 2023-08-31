package com.whyranoid.walkie

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.whyranoid.runningdata.RunningDataManager
import com.whyranoid.runningdata.model.RunningState
import kotlinx.coroutines.delay

class RunningWorker(
    private val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val runningDataManager = RunningDataManager.getInstance()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_MS).build()
    private lateinit var locationCallback: LocationCallback

    override suspend fun doWork(): Result {
        if (startTracking().not()) {
            runningDataManager.finishRunning()
            return Result.failure()
        }

        setForeground(createForegroundInfo("워키 러닝 중~"))

        while ((runningDataManager.runningState.value is RunningState.NotRunning).not()) {
            delay(UPDATE_INTERVAL_MS)
            when (runningDataManager.runningState.value) {
                is RunningState.NotRunning -> break
                is RunningState.Paused -> continue
                is RunningState.Running -> runningDataManager.tick()
            }
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
        return Result.success()
    }

    @SuppressLint("PrivateResource")
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val title = "달려~ 달려~"

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        createChannel()

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setContentIntent(pendingIntent)
            .setSmallIcon(com.whyranoid.presentation.R.drawable.ic_running_screen_selected)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ForegroundInfo(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION,
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ForegroundInfo(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION,
            )
        }
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun createChannel() {
        val name = "활동 추적" // TODO 리소스 분리
        val descriptionText = "달리기 활동을 추적하는 알림 채널입니다."
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun startTracking(): Boolean {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    runningDataManager.setRunningState(location)
                } ?: run {
                    runningDataManager.pauseRunning()
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper(),
            )
            runningDataManager.startRunning()
            return true
        } catch (e: SecurityException) {
            runningDataManager.pauseRunning()
        } catch (e: Exception) {
            runningDataManager.pauseRunning()
        }
        return false
    }

    companion object {
        const val WORKER_NAME = "RunningWorker"
        const val NOTIFICATION_ID = 2000
        const val CHANNEL_ID = "Walkie Channel"
        const val UPDATE_INTERVAL_MS = 1000L
    }
}

fun startWorker(context: Context): LiveData<WorkInfo> {
    val workManager = WorkManager.getInstance(context)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val runningRequest = OneTimeWorkRequestBuilder<RunningWorker>()
        .setConstraints(constraints)
        .build()

    workManager
        .beginUniqueWork(
            RunningWorker.WORKER_NAME,
            ExistingWorkPolicy.KEEP,
            runningRequest,
        )
        .enqueue()

    return workManager.getWorkInfoByIdLiveData(runningRequest.id)
}
