package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow

class SignInViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    val signInState = MutableStateFlow(SignInState.AgreeState())
}

sealed class SignInState {
    data class AgreeState(
        val agreeService: Boolean = false,
        val agreePersonal: Boolean = false,
        val agreeGps: Boolean = false,
        val agreeMarketing: Boolean = false,
    ) : SignInState()

    data class UserNameState(
        val name: String = "",
        val isDuplicated: Boolean?,
        val nickName: String = "",
        val year: Int?,
        val month: Int?,
        val day: Int?,
        val phoneNumber: String = "",
        val checkNumber: String?,
        val isValidate: Boolean?,
    ) : SignInState()

    data class InfoState(
        val sex: Sex?,
        val height: Int?,
        val weight: Int?,
    ) : SignInState()

    object Done : SignInState()
}
