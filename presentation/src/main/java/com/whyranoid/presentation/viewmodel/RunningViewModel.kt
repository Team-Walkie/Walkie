package com.whyranoid.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.running.RunningData
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.model.running.RunningPosition
import com.whyranoid.domain.model.running.UserLocation
import com.whyranoid.domain.repository.RunningHistoryRepository
import com.whyranoid.domain.repository.RunningRepository
import com.whyranoid.domain.usecase.running.GetRunningFollowerUseCase
import com.whyranoid.domain.usecase.running.RunningFinishUseCase
import com.whyranoid.domain.usecase.running.RunningPauseOrResumeUseCase
import com.whyranoid.domain.usecase.running.RunningStartUseCase
import com.whyranoid.presentation.model.UiState
import com.whyranoid.presentation.model.running.RunningFollower
import com.whyranoid.presentation.model.running.RunningInfo
import com.whyranoid.presentation.model.running.SavingState
import com.whyranoid.presentation.model.running.TrackingMode
import com.whyranoid.presentation.util.BitmapConverter
import com.whyranoid.runningdata.RunningDataManager
import com.whyranoid.runningdata.model.RunningFinishData
import com.whyranoid.runningdata.model.RunningState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class RunningScreenSideEffect

data class RunningScreenState(
    val runningState: UiState<RunningState> = UiState.Idle,
    val runningFollowerState: UiState<List<RunningFollower>> = UiState.Idle,
    val likedCountState: UiState<Int> = UiState.Idle,
    val runningInfoState: UiState<RunningInfo> = UiState.Idle,
    val runningResultInfoState: UiState<RunningInfo> = UiState.Idle,
    val trackingModeState: UiState<TrackingMode> = UiState.Idle,
    val runningFinishState: UiState<RunningFinishData> = UiState.Idle,
    val userLocationState: UiState<UserLocation> = UiState.Idle,
    val editState: UiState<Boolean> = UiState.Idle,
    val selectedImage: UiState<Uri> = UiState.Idle,
    val savingState: UiState<SavingState> = UiState.Idle,
)

class RunningViewModel(
    val runningStartUseCase: RunningStartUseCase,
    val runningPauseOrResumeUseCase: RunningPauseOrResumeUseCase,
    val runningFinishUseCase: RunningFinishUseCase,
    val getRunningFollowerUseCase: GetRunningFollowerUseCase,
    private val runningRepository: RunningRepository,
    private val runningHistoryRepository: RunningHistoryRepository,
) : ViewModel(), ContainerHost<RunningScreenState, RunningScreenSideEffect> {

    private val runningDataManager = RunningDataManager.getInstance()
    var startWorker: (() -> Unit)? = null

    override val container = container<RunningScreenState, RunningScreenSideEffect>(
        RunningScreenState(),
    )

    fun getRunningState() {
        runningRepository.listenLocation()
        viewModelScope.launch {
            runningRepository.userLocationState.collect {
                intent {
                    reduce {
                        state.copy(userLocationState = UiState.Success(it))
                    }
                }
            }
        }
        viewModelScope.launch {
            runningDataManager.runningState.collect { runningState ->
                if (runningState.runningData.lastLocation != null) {
                    runningRepository.removeUserLocation()
                }
                intent {
                    reduce {
                        val runningInfo =
                            runningState.runningData.toWalikeRunningData().toRunningInfo()
                        state.copy(
                            runningState = UiState.Success(runningState),
                            runningInfoState = UiState.Success(runningInfo),
                            trackingModeState = UiState.Success(
                                state.trackingModeState.getDataOrNull() ?: TrackingMode.FOLLOW,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun startRunning() {
        startWorker?.invoke()
        runningRepository.removeListener()
        intent {
            reduce {
                state.copy(trackingModeState = UiState.Success(TrackingMode.FOLLOW))
            }
        }
    }

    fun pauseRunning() {
        runningDataManager.pauseRunning()
    }

    fun resumeRunning() {
        runningDataManager.resumeRunning().onSuccess {
            intent {
                reduce {
                    state.copy(trackingModeState = UiState.Success(TrackingMode.FOLLOW))
                }
            }
        }
    }

    fun finishRunning() {
        intent {
            state.runningInfoState.getDataOrNull()?.let {
                reduce {
                    state.copy(runningResultInfoState = UiState.Success(it))
                }
            }
        }
        runningDataManager.finishRunning().onSuccess { runningFinishData ->
            intent {
                reduce {
                    state.copy(runningFinishState = UiState.Success(runningFinishData))
                }
            }
        }
    }

    fun onTrackingButtonClicked() {
        intent {
            reduce {
                state.copy(
                    trackingModeState = UiState.Success(
                        when (state.trackingModeState.getDataOrNull()) {
                            TrackingMode.NONE -> {
                                TrackingMode.NO_FOLLOW
                            }
                            TrackingMode.NO_FOLLOW -> {
                                TrackingMode.FOLLOW
                            }
                            TrackingMode.FOLLOW -> {
                                TrackingMode.NONE
                            }
                            else -> {
                                TrackingMode.FOLLOW
                            }
                        },
                    ),
                )
            }
        }
    }

    fun onTrackingCanceledByGesture() {
        intent {
            reduce {
                state.copy(
                    trackingModeState = UiState.Success(TrackingMode.NONE),
                )
            }
        }
    }

    fun openEdit() {
        intent {
            reduce {
                state.copy(editState = UiState.Success(true))
            }
        }
    }

    fun closeEdit() {
        intent {
            reduce {
                state.copy(editState = UiState.Success(false))
            }
        }
    }

    fun selectImage(uri: Uri) {
        intent {
            reduce {
                state.copy(selectedImage = UiState.Success(uri))
            }
        }
    }

    fun saveHistory(bitmap: Bitmap, finishData: RunningFinishData) {
        intent {
            runningHistoryRepository.saveRunningHistory(
                RunningHistory(
                    0L,
                    RunningData(
                        finishData.runningHistory.totalDistance,
                        finishData.runningHistory.pace,
                        finishData.runningHistory.totalRunningTime,
                        (finishData.runningHistory.totalDistance * 0.07).toInt(),
                        (finishData.runningHistory.totalDistance * 1.312).toInt(),
                        finishData.runningPositionList.map { list ->
                            list.map { runningPosition ->
                                RunningPosition(
                                    runningPosition.longitude,
                                    runningPosition.latitude,
                                )
                            }
                        },
                    ),
                    System.currentTimeMillis(),
                    BitmapConverter.bitmapToString(bitmap),
                ),
            ).onSuccess {
                reduce {
                    state.copy(
                        savingState = UiState.Idle,
                        runningFinishState = UiState.Idle,
                        selectedImage = UiState.Idle,
                        editState = UiState.Idle,
                        runningResultInfoState = UiState.Idle,
                    )
                }
                runningRepository.listenLocation()
            }
        }
    }

    fun takeSnapShot(runningFinishData: RunningFinishData) {
        intent {
            reduce {
                state.copy(savingState = UiState.Success(SavingState.Start(runningFinishData)))
            }
        }
    }

    companion object {
        const val MAP_MAX_ZOOM = 18.0
        const val MAP_MIN_ZOOM = 10.0
    }
}

fun MogakRunningData.toWalikeRunningData(): WalkieRunningData {
    return WalkieRunningData(
        distance = this.totalDistance,
        pace = this.pace,
        totalRunningTime = this.runningTime,
        calories = (this.totalDistance * 0.07).toInt(),
        steps = (this.totalDistance * 1.312).toInt(),
        paths = this.runningPositionList.map { list ->
            list.map { runningPosition ->
                RunningPosition(
                    runningPosition.longitude,
                    runningPosition.latitude,
                )
            }
        },
    )
}

fun WalkieRunningData.toRunningInfo(): RunningInfo {
    return RunningInfo(
        distance = this.distance,
        pace = this.pace,
        runningTime = this.totalRunningTime,
        calories = this.calories.toDouble(),
        steps = this.steps,
    )
}

typealias MogakRunningData = com.whyranoid.runningdata.model.RunningData
typealias WalkieRunningData = com.whyranoid.domain.model.running.RunningData
