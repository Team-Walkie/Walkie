package com.whyranoid.domain.usecase.running

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.FollowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first

class GetRunningFollowerUseCase(
    private val accountRepository: AccountRepository,
    private val followRepository: FollowRepository,
) {
    suspend operator fun invoke(): Flow<Pair<List<User>, List<User>>> {
        var id = -1L
        var followings = listOf<User>()
        var runningFollowings = listOf<User>()
        kotlin.runCatching {
            id = requireNotNull(accountRepository.uId.first())
        }
        return callbackFlow {
            while (true) {
                delay(100000)
                val followingsResponse = followRepository.getFollowings(id).getOrDefault(listOf())
                val runningFollowingsResponse =
                    followRepository.getWalkingFollowings(id).getOrDefault(listOf())
                if (followings != followingsResponse) {
                    followings = followingsResponse
                    runningFollowings = runningFollowingsResponse
                    send(Pair(runningFollowings, followings - runningFollowings.toSet()))
                }
            }
        }
    }
}
