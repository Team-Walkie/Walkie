package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.FollowRepository

class GetFollowingUseCase(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(uid: Long): Result<List<User>> {
        return followRepository.getFollowings(uid)
    }
}
