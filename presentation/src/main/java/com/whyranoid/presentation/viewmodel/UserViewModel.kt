package com.whyranoid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.usecase.GetChallengingPreviewsUseCase
import com.whyranoid.domain.usecase.GetPostUseCase
import com.whyranoid.domain.usecase.GetUserBadgesUseCase
import com.whyranoid.domain.usecase.GetUserDetailUseCase
import com.whyranoid.domain.usecase.GetUserPostPreviewsUseCase
import com.whyranoid.domain.usecase.SignOutUseCase
import com.whyranoid.domain.usecase.community.FollowUseCase
import com.whyranoid.domain.usecase.community.UnFollowUseCase
import com.whyranoid.domain.util.EMPTY
import com.whyranoid.presentation.model.UiState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.util.Date

sealed class UserPageSideEffect

data class UserPageState(
    val userDetailState: UiState<UserDetail> = UiState.Idle,
    val userBadgesState: UiState<List<Badge>> = UiState.Idle,
    val userPostPreviewsState: UiState<List<PostPreview>> = UiState.Idle,
    val calendarPreviewsState: UiState<List<PostPreview>> = UiState.Idle,
    val challengingPreviewsState: UiState<List<ChallengePreview>> = UiState.Idle,
)

class UserPageViewModel(
    val accountRepository: AccountRepository,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserBadgesUseCase: GetUserBadgesUseCase,
    private val getUserPostPreviewsUseCase: GetUserPostPreviewsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
    private val getChallengingPreviewsUseCase: GetChallengingPreviewsUseCase,
) : ViewModel(), ContainerHost<UserPageState, UserPageSideEffect> {

    override val container = container<UserPageState, UserPageSideEffect>(UserPageState())

    // todo isFollowing 포함, 데이터 가져올 떄 까지 loading 처리
    fun getUserDetail(uid: Long, isFollowing: Boolean?) = intent {
        reduce {
            state.copy(userDetailState = UiState.Loading)
        }
        getUserDetailUseCase(uid).onSuccess { userDetail ->
            reduce {
                state.copy(
                    userDetailState = UiState.Success(
                        userDetail.copy(
                            isFollowing = isFollowing,
                            postCount = state.userDetailState.getDataOrNull()?.postCount
                        )
                    ),
                )
            }
        }.onFailure {
            Log.d("userDetail", it.message.toString())
            reduce {
                state.copy(userDetailState = UiState.Error(it.message.toString()))
            }
        }
    }

    fun getUserBadges(uid: Long) = intent {
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

    fun getUserPostPreviews(uid: Long) = intent {
        reduce {
            state.copy(userPostPreviewsState = UiState.Loading)
        }
        getUserPostPreviewsUseCase(uid).onSuccess { userPostPreviews ->
            reduce {
                state.copy(
                    userPostPreviewsState = UiState.Success(userPostPreviews),
                    userDetailState = UiState.Success(
                        UserDetail(
                            state.userDetailState.getDataOrNull()?.user
                                ?: User.DUMMY.copy(imageUrl = String.EMPTY), // TODO 사람 실루엣 url
                            userPostPreviews.size,
                            state.userDetailState.getDataOrNull()?.followerCount ?: 0,
                            state.userDetailState.getDataOrNull()?.followingCount ?: 0,
                            state.userDetailState.getDataOrNull()?.isFollowing,
                        ),
                    ),
                )
            }
        }.onFailure {
            reduce {
                state.copy(userPostPreviewsState = UiState.Error(it.message.toString()))
            }
        }
    }

    fun selectDate(localDate: LocalDate) = intent {
        reduce {
            state.copy(calendarPreviewsState = UiState.Loading)
        }
        reduce {
            val posts = state.userPostPreviewsState.getDataOrNull() ?: emptyList()
            val filtered = posts.filter {
                val date = Date(it.date)
                date.year + 1900 == localDate.year && date.month + 1 == localDate.monthValue && date.date == localDate.dayOfMonth
            }
            state.copy(calendarPreviewsState = UiState.Success(filtered))
        }
    }

    fun follow(uid: Long) = intent {
        viewModelScope.launch {
            followUseCase(uid).onSuccess {
                reduce {
                    state.copy(
                        userDetailState = UiState.Success(
                            requireNotNull(state.userDetailState.getDataOrNull()).copy(
                                isFollowing = true,
                                followerCount = (
                                        state.userDetailState.getDataOrNull()?.followerCount
                                            ?: 0
                                        ) + 1,
                            ),
                        ),
                    )
                }
            }
        }
    }

    fun unFollow(uid: Long) = intent {
        viewModelScope.launch {
            unFollowUseCase(uid).onSuccess {
                reduce {
                    state.copy(
                        userDetailState = UiState.Success(
                            requireNotNull(state.userDetailState.getDataOrNull()).copy(
                                isFollowing = false,
                                followerCount = (
                                        state.userDetailState.getDataOrNull()?.followerCount
                                            ?: 0
                                        ) - 1,
                            ),
                        ),
                    )
                }
            }
        }
    }

    fun getChallengingPreviews(uid: Long) = intent {
        reduce {
            state.copy(challengingPreviewsState = UiState.Loading)
        }
        getChallengingPreviewsUseCase(uid.toInt()).onSuccess { list ->
            reduce {
                state.copy(
                    challengingPreviewsState = UiState.Success(list),
                )
            }
        }.onFailure {
            reduce {
                state.copy(challengingPreviewsState = UiState.Error(it.message.toString()))
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}
