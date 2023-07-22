package com.whyranoid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.running.RunningPosition
import com.whyranoid.domain.model.running.UserLocation
import com.whyranoid.domain.repository.RunningRepository
import com.whyranoid.domain.usecase.running.GetRunningFollowerUseCase
import com.whyranoid.domain.usecase.running.RunningFinishUseCase
import com.whyranoid.domain.usecase.running.RunningPauseOrResumeUseCase
import com.whyranoid.domain.usecase.running.RunningStartUseCase
import com.whyranoid.presentation.model.UiState
import com.whyranoid.presentation.model.running.RunningFollower
import com.whyranoid.presentation.model.running.RunningInfo
import com.whyranoid.presentation.model.running.TrackingMode
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
)

class RunningViewModel(
    val runningStartUseCase: RunningStartUseCase,
    val runningPauseOrResumeUseCase: RunningPauseOrResumeUseCase,
    val runningFinishUseCase: RunningFinishUseCase,
    val getRunningFollowerUseCase: GetRunningFollowerUseCase,
    private val runningRepository: RunningRepository,
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
        Log.d("vtag RunningViewModel", "pauseRunning") // TODO remove
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
