package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetSearchedUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFriendViewModel(
    private val getSearchedUserUseCase: GetSearchedUserUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    val userList = MutableStateFlow(listOf<User>())

    fun searchNickname(keyword: String) {
        _query.value = keyword
        viewModelScope.launch {
            getSearchedUserUseCase.invoke(keyword).onSuccess {
                userList.value = it
            }
        }
    }
}
