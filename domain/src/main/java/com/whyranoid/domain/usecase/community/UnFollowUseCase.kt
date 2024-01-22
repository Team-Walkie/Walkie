package com.whyranoid.domain.usecase.community

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.FollowRepository
import kotlinx.coroutines.flow.first

class UnFollowUseCase(
    private val accountRepository: AccountRepository,
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(otherUId: Long) {
        runCatching {
            val uid = requireNotNull(accountRepository.uId.first())
            followRepository.unfollow(uid, otherUId)
        }
    }
}
