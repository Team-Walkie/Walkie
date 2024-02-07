package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.FollowRepository

class GetMyFollowingUseCase(
    private val accountRepository: AccountRepository,
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(): Result<List<User>> {
        return followRepository.getFollowings(accountRepository.getUID())
    }
}
