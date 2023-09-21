package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.usecase.UploadPostUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AddPostViewModel(private val uploadPostUseCase: UploadPostUseCase) : ViewModel() {
    val uploadEvent = MutableSharedFlow<Boolean>()

    fun uploadPost(
        content: String,
        textVisibleState: TextVisibleState,
        history: String,
        imagePath: String,
    ) {
        println("AddPostViewModel") // TODO REMOVE
        viewModelScope.launch {
            uploadPostUseCase(content, textVisibleState.ordinal, history, imagePath).onFailure {
                println("AddPostViewModel Failure ${it.message}")
            }.onSuccess {
                println("AddPostViewModel Success $it")
            }
        }
    }
}

enum class TextVisibleState {
    WHITE, BLACK, HIDE
}
