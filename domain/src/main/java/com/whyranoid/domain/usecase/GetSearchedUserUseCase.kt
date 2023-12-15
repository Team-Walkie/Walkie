package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.FollowRepository
import com.whyranoid.domain.repository.OtherUserRepository

class GetSearchedUserUseCase(
    private val otherUserRepository: OtherUserRepository,
    private val followRepository: FollowRepository,
) {
    // operator fun invoke(query: String) = otherUserRepository.searchUsers(query).flow
    suspend operator fun invoke(query: String): Result<List<User>> {
        return followRepository.searchNickname(query)
    }
}
