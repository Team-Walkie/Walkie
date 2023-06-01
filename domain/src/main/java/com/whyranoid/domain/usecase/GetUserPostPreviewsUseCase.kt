package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.repository.PostRepository

class GetUserPostPreviewsUseCase(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(uid: String): Result<List<PostPreview>> {
        return postRepository.getUserPostPreviews(uid)
    }
}
