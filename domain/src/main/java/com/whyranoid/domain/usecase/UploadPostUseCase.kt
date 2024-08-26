package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UploadPostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(
        content: String,
        colorMode: Int,
        history: String,
        imagePath: String,
    ): Result<String> {
        return accountRepository.walkieId.first()?.let { uid ->
            postRepository.uploadPost(uid, content, colorMode, history, imagePath)
        } ?: kotlin.run {
            Result.failure(Exception("Account Error"))
        }
    }
}
