package com.whyranoid.domain.usecase.community

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.FollowRepository
import kotlinx.coroutines.flow.first

class FollowUseCase(
    private val accountRepository: AccountRepository,
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(other: User) {
        runCatching {
            val uid = requireNotNull(accountRepository.uId.first())
            followRepository.follow(uid, other.uid)
        }
    }
}
