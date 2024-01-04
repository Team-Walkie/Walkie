package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.first

class LikePostUseCase(
    private val communityRepository: CommunityRepository,
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(postId: Long): Result<Long> {
        val uid = requireNotNull(accountRepository.uId.first())
        return communityRepository.likePost(postId, uid)
    }
}
