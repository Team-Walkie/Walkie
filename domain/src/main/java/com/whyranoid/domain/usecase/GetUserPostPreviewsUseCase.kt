package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.repository.PostRepository

class GetUserPostPreviewsUseCase(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(uid: Long): Result<List<PostPreview>> {
        return postRepository.getMyPostPreviews(uid)
    }

    suspend operator fun invoke(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>> {
        return postRepository.getMyPostPreviews(uid, year, month, day)
    }
}
