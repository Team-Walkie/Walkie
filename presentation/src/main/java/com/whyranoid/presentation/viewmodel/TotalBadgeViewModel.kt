package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.usecase.GetMyUidUseCase
import com.whyranoid.domain.usecase.GetUserBadgesUseCase
import com.whyranoid.presentation.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed interface TotalBadgeSideEffect

data class TotalBadgeState(
    val badges: UiState<List<Badge>> = UiState.Idle
)

class TotalBadgeViewModel(
    private val getUserBadgesUseCase: GetUserBadgesUseCase,
    private val getMyUidUseCase: GetMyUidUseCase
): ViewModel(), ContainerHost<TotalBadgeState, TotalBadgeSideEffect> {

    override val container =
        container<TotalBadgeState, TotalBadgeSideEffect>(TotalBadgeState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMyUidUseCase().onSuccess { uid ->
                getBadges(uid)
            }
        }
    }

    private suspend fun getBadges(uid: Long) = intent {
        val result = getUserBadgesUseCase(uid)
        result.onSuccess { badges ->
            reduce {
                state.copy(
                    badges = UiState.Success(badges),
                )
            }
        }
    }
}