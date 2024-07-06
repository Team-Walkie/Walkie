package com.whyranoid.presentation.screens.mypage.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {
    private val _isDuplicateNickName = MutableStateFlow<Boolean?>(null)
    val isDuplicateNickName = _isDuplicateNickName.asStateFlow()

    val name = accountRepository.userName
    val nick = accountRepository.nickName
    val profileImg = accountRepository.profileUrl

    fun checkDuplicateNickName(nickName: String) = viewModelScope.launch(Dispatchers.IO) {
        accountRepository.checkNickName(nickName)
            .onSuccess { (isDuplicate, _) ->
                _isDuplicateNickName.update { isDuplicate }
            }
            .onFailure { _isDuplicateNickName.update { null } }
    }
}
