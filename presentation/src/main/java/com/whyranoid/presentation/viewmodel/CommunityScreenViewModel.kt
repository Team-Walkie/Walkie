package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetFollowingsPostsUseCase
import com.whyranoid.domain.usecase.GetMyFollowingUseCase
import com.whyranoid.domain.usecase.LikePostUseCase
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed interface CommunityScreenSideEffect

data class CommunityScreenState(
    val posts: UiState<List<Post>> = UiState.Idle,
    val following: UiState<List<User>> = UiState.Idle,
    val isEveryPost: UiState<Boolean> = UiState.Success(true),
)

class CommunityScreenViewModel(
    private val getMyFollowingUseCase: GetMyFollowingUseCase,
    private val getFollowingsPostsUseCase: GetFollowingsPostsUseCase,
    private val likePostUseCase: LikePostUseCase,
) : ViewModel(), ContainerHost<CommunityScreenState, CommunityScreenSideEffect> {

    override val container =
        container<CommunityScreenState, CommunityScreenSideEffect>(CommunityScreenState())

    init {
        intent {
            val result = getMyFollowingUseCase()
            result.onSuccess { myFollowing ->
                reduce {
                    state.copy(
                        following = UiState.Success(myFollowing),
                    )
                }
            }
        }
        getPosts()
    }

    fun switchPostType() = intent {
        reduce {
            state.copy(isEveryPost = UiState.Success(state.isEveryPost.getDataOrNull()!!.not()))
        }
        getPosts()
    }

    fun getPosts() = intent {
        reduce {
            state.copy(posts =  UiState.Loading)
        }
        val isEveryPost = state.isEveryPost.getDataOrNull() ?: true
        val result = getFollowingsPostsUseCase(isEveryPost)
        result.onSuccess { posts ->
            reduce {
                state.copy(
                    posts = UiState.Success(
                        (state.posts.getDataOrNull() ?: emptyList()) +
                            posts,
                    ),
                )
            }
        }
    }

    fun likePost(postId: Long) = intent {
        val result = likePostUseCase(postId)

        result.onSuccess { updatedLikeCount ->

            reduce {
                state.copy(
                    posts = UiState.Success(
                        state.posts.getDataOrNull()?.map {
                            if (it.id == postId) {
                                it.copy(
                                    likeCount = if (updatedLikeCount == -1L) it.likeCount - 1 else updatedLikeCount.toInt(),
                                    isLiked = it.isLiked.not(),
                                )
                            } else {
                                it
                            }
                        } ?: emptyList(),
                    ),
                )
            }
        }.onFailure {
            // TODO: Error handling
        }
    }
}
