package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.usecase.GetSearchedUserUseCase

class SearchFriendViewModel(
    private val getSearchedUserUseCase: GetSearchedUserUseCase
): ViewModel() {

    fun searchUsers(query: String) = getSearchedUserUseCase(query)
}