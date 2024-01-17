package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.UserDetail
import com.whyranoid.domain.repository.FollowRepository
import com.whyranoid.domain.repository.UserRepository

class GetUserDetailUseCase(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(uid: Long): Result<UserDetail> {
        return kotlin.runCatching {
            val user = userRepository.getUser(uid).getOrThrow()
            val followingCount = followRepository.getFollowings(user.uid).getOrThrow().size
            val followerCount = followRepository.getFollowers(user.uid).getOrThrow().size
            UserDetail(user, 0, followerCount, followingCount, true)
        }
    }
}
