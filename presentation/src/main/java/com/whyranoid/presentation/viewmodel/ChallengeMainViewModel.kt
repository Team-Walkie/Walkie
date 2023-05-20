package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType
import com.whyranoid.domain.usecase.GetChallengePreviewsByTypeUseCase
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetNewChallengePreviewsUseCase
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeMainSideEffect {

}

data class ChallengeMainState(
    val newChallengePreviewsState: UiState<List<ChallengePreview>> = UiState.Idle,
    val challengingPreviewsState: UiState<List<ChallengePreview>> = UiState.Idle,
    val typedChallengePreviewsState: UiState<List<List<ChallengePreview>>> = UiState.Idle,
)

class ChallengeMainViewModel(
    private val getNewChallengePreviewsUseCase: GetNewChallengePreviewsUseCase,
    private val getChallengingPreviewsUseCase: GetChallengingPreviewsUseCase,
    private val getChallengePreviewsByTypeUseCase: GetChallengePreviewsByTypeUseCase
) : ViewModel(), ContainerHost<ChallengeMainState, ChallengeMainSideEffect> {

    override val container =
        container<ChallengeMainState, ChallengeMainSideEffect>(ChallengeMainState())

    init {
        getNewChallengeItems()
        getChallengingItems()
        getTypedChallengeItems()
    }

    private fun getNewChallengeItems() = intent {
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

    private fun getChallengingItems() = intent {
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

    private fun getTypedChallengeItems() = intent {
        reduce {
            state.copy(typedChallengePreviewsState = UiState.Loading)
        }
        val typedChallengePreviews =
            MutableList(ChallengeType.values().size) { emptyList<ChallengePreview>() }
        ChallengeType.values().forEachIndexed { index, challengeType ->
            typedChallengePreviews[index] = getChallengePreviewsByTypeUseCase(challengeType)
        }
        reduce {
            state.copy(
                typedChallengePreviewsState = UiState.Success(typedChallengePreviews)
            )
        }
    }

}