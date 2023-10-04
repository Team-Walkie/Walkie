package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.post.TextVisibleState
import com.whyranoid.domain.usecase.UploadPostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddPostViewModel(private val uploadPostUseCase: UploadPostUseCase) : ViewModel() {
    val uploadingState = MutableStateFlow(PostUploadingState.Init)

    fun startUploading() {
        uploadingState.value = PostUploadingState.Uploading
    }

    fun uploadPost(
        content: String,
        textVisibleState: TextVisibleState,
        history: String,
        imagePath: String,
    ) {
        println("AddPostViewModel") // TODO REMOVE
        viewModelScope.launch {
            uploadPostUseCase(content, textVisibleState.ordinal, history, imagePath).onFailure {
                uploadingState.value = PostUploadingState.Error
            }.onSuccess {
                uploadingState.value = PostUploadingState.Done
            }
        }
    }
}

enum class PostUploadingState {
    Init, Uploading, Done, Error
}
