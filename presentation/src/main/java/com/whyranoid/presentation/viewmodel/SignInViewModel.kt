package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    val signInState: MutableStateFlow<SignInState> =
        MutableStateFlow(SignInState.InitialState)

    fun goToAgreeState(authId: String, userName: String, profileUrl: String?) {
        signInState.value = SignInState.AgreeState(
            authId = authId,
            userName = userName,
            profileUrl = profileUrl,
        )
    }

    fun goToUserNameState() {
        (signInState.value as? SignInState.AgreeState)?.let { state ->
            signInState.value = SignInState.UserNameState(
                authId = state.authId,
                name = state.userName,
                profileUrl = state.profileUrl,
                agreeGps = state.agreeGps,
                agreeMarketing = state.agreeMarketing,
            )
        }
    }

    fun goToInfoState() {
        (signInState.value as? SignInState.UserNameState)?.let { state ->
            signInState.value = SignInState.InfoState(
                authId = state.authId,
                name = state.name,
                profileUrl = state.profileUrl,
                nickName = state.nickName,
                year = requireNotNull(state.year),
                month = requireNotNull(state.month),
                day = requireNotNull(state.day),
                phoneNumber = state.phoneNumber,
                agreeGps = state.agreeGps,
                agreeMarketing = state.agreeMarketing,
            )
        }
    }

    // TODO 회원가입 완료 처리
    fun goToDoneState() {
        (signInState.value as? SignInState.InfoState)?.let { state ->
            viewModelScope.launch {
                accountRepository.signUp(
                    authId = state.authId,
                    userName = state.name,
                    profileUrl = state.profileUrl,
                    nickName = state.nickName,
                    birthDay = "${state.year}-${state.month}-${state.day}",
                    phoneNumber = state.phoneNumber,
                    sex = requireNotNull(state.sex),
                    height = requireNotNull(state.height),
                    weight = requireNotNull(state.weight),
                    agreeGps = state.agreeGps,
                    agreeSubscription = state.agreeMarketing,
                )
            }
        }
    }

    // TODO 중복 확인 로직 추가
    fun checkDupNickName(authId: String, nickName: String) {
        (signInState.value as? SignInState.UserNameState)?.let { state ->
            signInState.value = state.copy(isDuplicated = false)
        }
    }

    // TODO 인증 확인 로직 추가
    fun checkValidationNumber(authId: String, number: String) {
        (signInState.value as? SignInState.UserNameState)?.let { state ->
            signInState.value = state.copy(isValidate = true)
        }
    }
}

sealed class SignInState {
    object InitialState : SignInState()
    data class AgreeState(
        val authId: String,
        val userName: String,
        val profileUrl: String? = null,
        val agreeService: Boolean = false,
        val agreePersonal: Boolean = false,
        val agreeGps: Boolean = false,
        val agreeMarketing: Boolean = false,
    ) : SignInState()

    data class UserNameState(
        val authId: String,
        val name: String = "",
        val profileUrl: String? = null,
        val isDuplicated: Boolean? = null,
        val nickName: String = "",
        val year: Int? = null,
        val month: Int? = null,
        val day: Int? = null,
        val phoneNumber: String = "",
        val checkNumber: String? = null,
        val isValidate: Boolean? = null,
        val agreeGps: Boolean = false,
        val agreeMarketing: Boolean = false,
    ) : SignInState()

    data class InfoState(
        val authId: String,
        val name: String = "",
        val profileUrl: String? = null,
        val nickName: String = "",
        val year: Int,
        val month: Int,
        val day: Int,
        val phoneNumber: String = "",
        val agreeGps: Boolean = false,
        val agreeMarketing: Boolean = false,
        val sex: Sex? = null,
        val height: Int? = null,
        val weight: Int? = null,
    ) : SignInState()

    object Done : SignInState()
}
