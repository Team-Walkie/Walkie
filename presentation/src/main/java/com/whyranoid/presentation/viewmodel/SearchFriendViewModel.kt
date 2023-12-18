package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserWithFollowingState
import com.whyranoid.domain.usecase.community.FollowUseCase
import com.whyranoid.domain.usecase.community.GetSearchedUserUseCase
import com.whyranoid.domain.usecase.community.UnFollowUseCase
import com.whyranoid.domain.util.EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFriendViewModel(
    private val getSearchedUserUseCase: GetSearchedUserUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow(String.EMPTY)
    val query get() = _query.asStateFlow()

    val userList = MutableStateFlow(listOf<UserWithFollowingState>())

    fun searchNickname(keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            getSearchedUserUseCase.invoke(keyword).onSuccess {
                userList.value = it
            }
        }
    }

    fun follow(other: User) {
        viewModelScope.launch {
            followUseCase(other)
        }
    }

    fun unFollow(other: User) {
        viewModelScope.launch {
            unFollowUseCase(other)
        }
    }
}
