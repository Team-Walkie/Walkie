package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.model.user.UserDetail
import com.whyranoid.domain.usecase.*
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed class UserPageSideEffect

data class UserPageState(
    val userDetailState: UiState<UserDetail> = UiState.Idle,
    val userBadgesState: UiState<List<Badge>> = UiState.Idle,
    val userPostPreviewsState: UiState<List<PostPreview>> = UiState.Idle,
)

class UserPageViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserBadgesUseCase: GetUserBadgesUseCase,
    private val getUserPostPreviewsUseCase: GetUserPostPreviewsUseCase,
    private val getPostUseCase: GetPostUseCase,
) : ViewModel(), ContainerHost<UserPageState, UserPageSideEffect> {

    override val container =
        container<UserPageState, UserPageSideEffect>(UserPageState())

    fun getUserDetail(uid: String) = intent {
        reduce {
            state.copy(userDetailState = UiState.Loading)
        }
        getUserDetailUseCase(uid).onSuccess { userDetail ->
            reduce {
                state.copy(
                    userDetailState = UiState.Success(userDetail),
                )
            }
        }.onFailure {
            reduce {
                state.copy(userDetailState = UiState.Error(it.message.toString()))
            }
        }
    }

    fun getUserBadges(uid: String) = intent {
        reduce {
            state.copy(userBadgesState = UiState.Loading)
        }
        getUserBadgesUseCase(uid).onSuccess { userBadges ->
            reduce {
                state.copy(
                    userBadgesState = UiState.Success(userBadges),
                )
            }
        }.onFailure {
            reduce {
                state.copy(userBadgesState = UiState.Error(it.message.toString()))
            }
        }
    }

    fun getUserPostPreviews(uid: String) = intent {
        reduce {
            state.copy(userPostPreviewsState = UiState.Loading)
        }
        getUserPostPreviewsUseCase(uid).onSuccess { userPostPreviews ->
            reduce {
                state.copy(
                    userPostPreviewsState = UiState.Success(userPostPreviews),
                )
            }
        }.onFailure {
            reduce {
                state.copy(userPostPreviewsState = UiState.Error(it.message.toString()))
            }
        }
    }
}
