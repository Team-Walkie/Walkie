package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalTime

class SplashViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val _splashState: MutableStateFlow<SplashState> =
        MutableStateFlow(SplashState.InitialState)
    val splashState: StateFlow<SplashState> get() = _splashState.asStateFlow()

    val isDay = LocalTime.now().hour in 6..18

    suspend fun splashStart() {
        viewModelScope.launch {
            delay(2000)
            _splashState.value =
                if (isSignedIn()) SplashState.SignedInState else SplashState.SignInState

            accountRepository.walkieId.collect {
                if (it == null) {
                    _splashState.value = SplashState.SignInState
                }
            }
        }
    }

    fun finishSignIn() {
        _splashState.value = SplashState.SignedInState
    }

    private suspend fun isSignedIn(): Boolean {
        return accountRepository.walkieId.first() != null
    }
}

enum class SplashState {
    SignInState, SignedInState, InitialState
}
