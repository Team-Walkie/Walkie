package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetNewChallengePreviewsUseCase
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeSideEffect {

}

data class ChallengeState(
    val newChallengePreviews: List<ChallengePreview> = emptyList(),
    val challengingPreviews: List<ChallengePreview> = emptyList(),
)

class ChallengeViewModel(
    private val getNewChallengePreviewsUseCase: GetNewChallengePreviewsUseCase,
    private val getChallengingPreviewsUseCase: GetChallengingPreviewsUseCase
) : ViewModel(), ContainerHost<ChallengeState, ChallengeSideEffect> {

    override val container = container<ChallengeState, ChallengeSideEffect>(ChallengeState())

    init {
        getNewChallengeItems()
        getChallengingItems()
    }

    private fun getNewChallengeItems() = intent {
        viewModelScope.launch {
            val newChallengePreviews = getNewChallengePreviewsUseCase()
            reduce {
                state.copy(
                    newChallengePreviews = newChallengePreviews
                )
            }
        }
    }

    private fun getChallengingItems() = intent {
        viewModelScope.launch {
            val challengingPreviews = getChallengingPreviewsUseCase()
            reduce {
                state.copy(
                    challengingPreviews = challengingPreviews
                )
            }
        }
    }


}