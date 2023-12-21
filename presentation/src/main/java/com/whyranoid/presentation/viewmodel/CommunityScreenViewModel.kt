package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetMyFollowingUseCase
import com.whyranoid.domain.usecase.GetFollowingsPostsUseCase
import com.whyranoid.presentation.model.UiState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

sealed interface CommunityScreenSideEffect {

}

data class CommunityScreenState(
    val posts: UiState<List<Post>> = UiState.Idle,
    val following: UiState<List<User>> = UiState.Idle,
)

class CommunityScreenViewModel(
    private val getMyFollowingUseCase: GetMyFollowingUseCase,
    private val getFollowingsPostsUseCase: GetFollowingsPostsUseCase
) : ViewModel(), ContainerHost<CommunityScreenState, CommunityScreenSideEffect> {

    override val container =
        container<CommunityScreenState, CommunityScreenSideEffect>(CommunityScreenState())

    init {
        intent {
            val result = getMyFollowingUseCase()
            result.onSuccess { myFollowing ->
                reduce {
                    state.copy(
                        following = UiState.Success(myFollowing)
                    )
                }
            }
        }
        getPosts()
    }

    fun getPosts() = intent {
        val result = getFollowingsPostsUseCase()
        println("결과 : $result")
        result.onSuccess { posts ->
            reduce {
                state.copy(
                    posts = UiState.Success(
                        (state.posts.getDataOrNull() ?: emptyList()) +
                                posts
                    )
                )
            }
        }
    }
}