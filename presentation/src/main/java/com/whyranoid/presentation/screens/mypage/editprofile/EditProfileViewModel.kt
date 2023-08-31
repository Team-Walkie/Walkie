package com.whyranoid.presentation.screens.mypage.editprofile

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.repository.AccountRepository

class EditProfileViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {
    val name = accountRepository.userName
    val nick = accountRepository.nickName
}
