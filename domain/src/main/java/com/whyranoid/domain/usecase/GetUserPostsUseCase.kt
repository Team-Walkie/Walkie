package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository

class GetUserPostsUseCase(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(uid: Long): Result<List<Post>> {
        return postRepository.getMyPosts(uid, accountRepository.getUID())
    }
}