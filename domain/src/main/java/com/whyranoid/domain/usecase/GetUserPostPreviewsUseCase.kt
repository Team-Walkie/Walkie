package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository
import kotlinx.coroutines.flow.first

class GetUserPostPreviewsUseCase(
    private val postRepository: PostRepository,
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(uid: Long): Result<List<PostPreview>> {
        val myUid = accountRepository.uId.first()
        return if (myUid == uid) {
            postRepository.getMyPostPreviews(uid)
        } else {
            postRepository.getUserPostPreviews(uid)
        }
    }

    suspend operator fun invoke(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>> {
        val myUid = accountRepository.uId.first()
        return if (myUid == uid) {
            postRepository.getMyPostPreviews(uid, year, month, day)
        } else {
            postRepository.getUserPostPreviews(uid, year, month, day)
        }
    }
}
