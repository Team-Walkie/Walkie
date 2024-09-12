package com.whyranoid.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetUserDetailUseCase
import com.whyranoid.domain.usecase.GetUserPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserPostsViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
) : ViewModel() {

    private val _userPostUiState: MutableStateFlow<UserPostUiState> =
        MutableStateFlow(UserPostUiState.Loading)
    val userPostUiState get() = _userPostUiState.asStateFlow()

    fun getUiData(uid: Long) = viewModelScope.launch {
        val user = getUserDetailUseCase(uid).getOrNull()?.user
        val posts = getUserPostsUseCase(uid).getOrNull()
        if (user != null && posts != null) {
            _userPostUiState.value = UserPostUiState.Success(user, posts)
        } else {
            _userPostUiState.value = UserPostUiState.Failed
        }
    }
}

sealed class UserPostUiState {
    data class Success(
        val user: User,
        val posts: List<Post>
    ) : UserPostUiState()

    object Loading : UserPostUiState()

    object Failed : UserPostUiState()
}
