package com.whyranoid.domain.usecase.community

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository
import kotlinx.coroutines.flow.first
import java.util.Date

class SendCommentUseCase(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(
        postId: Long,
        content: String,
    ): Result<Unit> {
        val commenterId = accountRepository.uId.first() ?: return Result.failure(Exception("uid is null"))
        return postRepository.sendComment(
            postId,
            commenterId,
            Date(System.currentTimeMillis()).toString(),
            content,
        )
    }
}
