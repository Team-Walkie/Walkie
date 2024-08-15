package com.whyranoid.presentation.screens.mypage.editprofile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {

    private val _isDuplicateNickName = MutableStateFlow<Boolean?>(null)
    val isDuplicateNickName = _isDuplicateNickName.asStateFlow()

    private val _isMyInfoChanged = MutableSharedFlow<Boolean>()
    val isMyInfoChanged = _isMyInfoChanged.asSharedFlow()

    private val _userInfoUiState = MutableStateFlow<UserInfoUiState?>(null)
    val userInfoUiState = _userInfoUiState.asStateFlow()

    val isChangeButtonEnabled = mutableStateOf(false)

    val walkieId = accountRepository.walkieId

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val walkieId = walkieId.firstOrNull() ?: return@launch

            accountRepository.getUserInfo(walkieId)
                .onSuccess { userInfo ->
                    _userInfoUiState.update {
                        UserInfoUiState(
                            userInfo.name,
                            userInfo.nickname,
                            userInfo.profileImg
                        )
                    }
                }
        }
    }

    fun checkDuplicateNickName(nickName: String) = viewModelScope.launch(Dispatchers.IO) {
        accountRepository.checkNickName(nickName)
            .onSuccess { (isDuplicate, _) ->
                _isDuplicateNickName.update { isDuplicate }
            }
            .onFailure { _isDuplicateNickName.update { null } }
    }

    fun changeMyInfo(walkieId: Long, nickName: String, profileUrl: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.changeMyInfo(walkieId, nickName, profileUrl)
                .onSuccess {
                    _isMyInfoChanged.emit(true)
                    _userInfoUiState.update { it?.copy(nickname = nickName, profileUrl = profileUrl) }
                }
        }

    fun setProfileUrl(url: String?) {
        _userInfoUiState.update { it?.copy(profileUrl = url) }
    }
}
