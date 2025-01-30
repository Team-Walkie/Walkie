package com.whyranoid.presentation.viewmodel.challenge

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.usecase.GetChallengeDetailUseCase
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeImageSaveSideEffect {

}

data class ChallengeImageSaveState(
    val challenge: UiState<Challenge> = UiState.Idle,
)

class ChallengeImageSaveViewModel(
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase
) : ViewModel(), ContainerHost<ChallengeImageSaveState, ChallengeImageSaveSideEffect> {

    override val container =
        container<ChallengeImageSaveState, ChallengeImageSaveSideEffect>(ChallengeImageSaveState())

    fun getChallengeDetail(challengeId: Long) = intent {
        reduce {
            state.copy(challenge = UiState.Loading)
        }

        val challenge = getChallengeDetailUseCase(challengeId)

        reduce {
            state.copy(challenge = UiState.Success(challenge))
        }
    }
}