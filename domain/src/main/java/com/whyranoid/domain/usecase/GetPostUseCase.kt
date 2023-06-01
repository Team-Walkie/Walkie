package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.repository.PostRepository

class GetPostUseCase(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(postId: Long): Result<Post> {
        return postRepository.getPost(postId)
    }
}
