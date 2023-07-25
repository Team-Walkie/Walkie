package com.whyranoid.presentation.model.running

import com.whyranoid.runningdata.model.RunningFinishData

sealed class SavingState {
    data class Start(val runningFinishData: RunningFinishData) : SavingState()
    object Done : SavingState()
}
