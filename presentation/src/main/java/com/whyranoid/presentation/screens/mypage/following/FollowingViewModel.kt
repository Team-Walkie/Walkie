package com.whyranoid.presentation.screens.mypage.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetFollowerUseCase
import com.whyranoid.domain.usecase.GetFollowingUseCase
import com.whyranoid.domain.usecase.GetMyUidUseCase
import com.whyranoid.domain.usecase.community.FollowUseCase
import com.whyranoid.domain.usecase.community.RemoveFollowerUseCase
import com.whyranoid.domain.usecase.community.UnFollowUseCase
import com.whyranoid.domain.util.EMPTY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowingViewModel(
    private val getFollowerUseCase: GetFollowerUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val getMyUidUseCase: GetMyUidUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
    private val removeFollowerUseCase: RemoveFollowerUseCase,
) : ViewModel() {
    val followingSearchText = MutableStateFlow(String.EMPTY)
    val followerSearchText = MutableStateFlow(String.EMPTY)

    private val _followingList = MutableStateFlow(emptyList<User>())
    private val _followerList = MutableStateFlow(emptyList<User>())

    val followingList = MutableStateFlow(emptyList<User>())
    val followerList = MutableStateFlow(emptyList<User>())

    val isMyList = MutableStateFlow(false)

    val removedFollowerList = MutableStateFlow(emptyList<Long>())

    fun loadList(uid: Long) {
        viewModelScope.launch {
            getMyUidUseCase().onSuccess { myUid ->
                isMyList.update { uid == myUid }
            }
        }

        viewModelScope.launch {
            getFollowingUseCase(uid).onSuccess {
                _followingList.value = it
            }
        }

        viewModelScope.launch {
            getFollowerUseCase(uid).onSuccess {
                _followerList.value = it
            }
        }

        viewModelScope.launch {
            _followingList.combine(followingSearchText) { list, text ->
                if (text.isEmpty()) list else list.filter { it.nickname.contains(text) }
            }.collectLatest {
                followingList.value = it
            }
        }

        viewModelScope.launch {
            _followerList.combine(followerSearchText) { list, text ->
                if (text.isEmpty()) list else list.filter { it.nickname.contains(text) }
            }.collectLatest {
                followerList.value = it
            }
        }
    }

    fun setFollowingSearchText(text: String) {
        followingSearchText.update { text }
    }

    fun setFollowerSearchText(text: String) {
        followerSearchText.update { text }
    }

    fun unfollow(uid: Long) {
        viewModelScope.launch {
            unFollowUseCase(uid)
        }
    }

    fun follow(uid: Long) {
        viewModelScope.launch {
            followUseCase(uid)
        }
    }

    fun removeFollower(uid: Long) {
        viewModelScope.launch {
            removeFollowerUseCase(uid).onSuccess {
                removedFollowerList.value = removedFollowerList.value + listOf(uid)
            }
        }
    }

    companion object {
        const val FOLLOWER_PAGE_NO = 0
        const val FOLLOWING_PAGE_NO = 1
    }
}
