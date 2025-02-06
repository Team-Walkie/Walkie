package com.whyranoid.domain.usecase.community

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository

class DeletePostUseCase(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(postId: Long): Result<Boolean> {
        val uid = accountRepository.getUID()
        return postRepository.deletePost(uid, postId)
    }
}
