package com.whyranoid.domain.usecase.community

import com.whyranoid.domain.model.user.UserWithFollowingState
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.FollowRepository

class GetSearchedUserUseCase(
    private val accountRepository: AccountRepository,
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(query: String): Result<List<UserWithFollowingState>> {
        return kotlin.runCatching {
            val uid = accountRepository.getUID()
            val followingList = requireNotNull(followRepository.getFollowings(uid).getOrNull())
            requireNotNull(followRepository.searchNickname(query).getOrNull()).map { user ->
                UserWithFollowingState(
                    user,
                    followingList.contains(user),
                )
            }.filter {
                it.user.uid != uid
            }
        }
    }
}
