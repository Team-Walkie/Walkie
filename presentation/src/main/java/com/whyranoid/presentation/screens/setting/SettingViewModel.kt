package com.whyranoid.presentation.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.usecase.GetMyUidUseCase
import com.whyranoid.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    getMyUidUseCase: GetMyUidUseCase,
    getUserUseCase: GetUserUseCase,
): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = getMyUidUseCase().getOrNull()
            val user = uid?.let { getUserUseCase(it).getOrNull() }
            _user.value = user
        }
    }
}