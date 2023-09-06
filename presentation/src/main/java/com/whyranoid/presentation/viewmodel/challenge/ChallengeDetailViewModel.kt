package com.whyranoid.presentation.viewmodel.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.usecase.GetChallengeDetailUseCase
import com.whyranoid.presentation.model.UiState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class ChallengeDetailSideEffect {

}

data class ChallengeDetailState(
    val challenge: UiState<Challenge> = UiState.Idle,
)

class ChallengeDetailViewModel(
    private val getChallengeDetailUseCase: GetChallengeDetailUseCase
) : ViewModel(), ContainerHost<ChallengeDetailState, ChallengeDetailSideEffect> {

    override val container =
        container<ChallengeDetailState, ChallengeDetailSideEffect>(ChallengeDetailState())

    fun getChallengeDetail(challengeId: Long) = intent{

        viewModelScope.launch {
            reduce {
                state.copy(challenge = UiState.Loading)
            }
            val challenge = getChallengeDetailUseCase(challengeId)
            // TODO: Error Handling
            reduce {
                state.copy(challenge = UiState.Success(challenge))
            }
        }

    }
}

