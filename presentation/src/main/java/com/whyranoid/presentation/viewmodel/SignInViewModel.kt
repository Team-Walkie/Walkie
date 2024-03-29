package com.whyranoid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _signInState: MutableStateFlow<SignInState> =
        MutableStateFlow(SignInState.InitialState())
    val signInState get() = _signInState.asStateFlow()

    fun setUserNameState(userNameState: SignInState.UserNameState) {
        _signInState.value = userNameState
    }

    fun setInfoState(infoState: SignInState.InfoState) {
        _signInState.value = infoState
    }

    fun goToAgreeState(authId: String, userName: String, profileUrl: String?) {
        _signInState.value = SignInState.AgreeState(
            authId = authId,
            userName = userName,
            profileUrl = profileUrl,
        )
    }

    fun goToUserNameState(agreeGps: Boolean, agreeMarketing: Boolean) {
        (signInState.value as? SignInState.AgreeState)?.let { state ->
            _signInState.value = SignInState.UserNameState(
                authId = state.authId,
                name = state.userName,
                profileUrl = state.profileUrl,
                agreeGps = agreeGps,
                agreeMarketing = agreeMarketing,
            )
        }
    }

    fun goToInfoState() {
        (signInState.value as? SignInState.UserNameState)?.let { state ->
            _signInState.value = SignInState.InfoState(
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

    fun goToDoneState() {
        (signInState.value as? SignInState.InfoState)?.let { state ->
            viewModelScope.launch {
                _signInState.value = state.copy(isProgress = true)

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
                ).onSuccess {
                    _signInState.value = state.copy(isProgress = false)
                    _signInState.value = SignInState.Done(state.name)
                }.onFailure {
                    // TODO: 실패 시
                    delay(1000)
                    _signInState.value = state.copy(isProgress = false)
                }
            }
        }
    }

    fun checkDupNickName() {
        (signInState.value as? SignInState.UserNameState)?.let { state ->
            viewModelScope.launch {
                accountRepository.checkNickName(state.nickName)
                    .onSuccess { (isDuplicated, nickName) ->
                        _signInState.value =
                            state.copy(isDuplicated = isDuplicated, dupResult = nickName)
                    }.onFailure {
                        Log.d("checkDupNickName", it.message.toString())
                    }
            }
        }
    }
}

sealed class SignInState {
    data class InitialState(
        val authId: String? = null,
        val userName: String? = null,
        val profileUrl: String? = null,
    ) : SignInState()

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
        val dupResult: String = "",
        val nickName: String = "",
        val year: Int? = null,
        val month: Int? = null,
        val day: Int? = null,
        val phoneNumber: String = "",
        val checkNumber: String? = null,
        val isValidate: Boolean? = null,
        val validateResult: String = "",
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
        val isProgress: Boolean = false,
    ) : SignInState()

    data class Done(val userName: String) : SignInState()
}
