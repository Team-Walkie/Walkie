package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetNewChallengePreviewsUseCase
import com.whyranoid.presentation.model.UiState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeMainSideEffect {

}

data class ChallengeMainState(
    val newChallengePreviewsState: UiState<List<ChallengePreview>> = UiState.Idle,
    val challengingPreviewsState: UiState<List<ChallengePreview>> = UiState.Idle,
)

class ChallengeMainViewModel(
    private val getNewChallengePreviewsUseCase: GetNewChallengePreviewsUseCase,
    private val getChallengingPreviewsUseCase: GetChallengingPreviewsUseCase
) : ViewModel(), ContainerHost<ChallengeMainState, ChallengeMainSideEffect> {

    override val container = container<ChallengeMainState, ChallengeMainSideEffect>(ChallengeMainState())

    init {
        getNewChallengeItems()
        getChallengingItems()
    }

    private fun getNewChallengeItems() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(newChallengePreviewsState = UiState.Loading)
            }
            val newChallengePreviews = getNewChallengePreviewsUseCase()
            reduce {
                state.copy(
                    newChallengePreviewsState = UiState.Success(newChallengePreviews)
                )
            }
        }
    }

    private fun getChallengingItems() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(challengingPreviewsState = UiState.Loading)
            }
            val challengingPreviews = getChallengingPreviewsUseCase()
            reduce {
                state.copy(
                    challengingPreviewsState = UiState.Success(challengingPreviews)
                )
            }
        }
    }


}