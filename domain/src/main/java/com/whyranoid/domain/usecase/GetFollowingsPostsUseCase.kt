package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.PostRepository
import kotlinx.coroutines.flow.first

class GetFollowingsPostsUseCase(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(): Result<List<Post>> {
        val myUid = requireNotNull(accountRepository.uId.first())
        return postRepository.getMyFollowingsPost(myUid)
    }
}