package com.whyranoid.presentation.viewmodel.challenge

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.usecase.GetChallengeDetailUseCase
import com.whyranoid.domain.usecase.StartChallengeUseCase
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeDetailSideEffect {

    object StartChallengeSuccess : ChallengeDetailSideEffect()
    object StartChallengeFailure : ChallengeDetailSideEffect()
}

data class ChallengeDetailState(
    val challenge: UiState<Challenge> = UiState.Idle,
)

class ChallengeDetailViewModel(
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase,
    private val startChallengeUseCase: StartChallengeUseCase
) : ViewModel(), ContainerHost<ChallengeDetailState, ChallengeDetailSideEffect> {

    override val container =
        container<ChallengeDetailState, ChallengeDetailSideEffect>(ChallengeDetailState())

    fun getChallengeDetail(challengeId: Long) = intent {
        reduce {
            state.copy(challenge = UiState.Loading)
        }
        val challenge = getChallengeDetailUseCase(challengeId)
        // TODO: Error Handling
        reduce {
            state.copy(challenge = UiState.Success(challenge))
        }
    }

    fun startChallenge(challengeId: Int) = intent {

        startChallengeUseCase(challengeId).onSuccess {
            postSideEffect(ChallengeDetailSideEffect.StartChallengeSuccess)
        }.onFailure {
            postSideEffect(ChallengeDetailSideEffect.StartChallengeFailure)
        }

    }
}

