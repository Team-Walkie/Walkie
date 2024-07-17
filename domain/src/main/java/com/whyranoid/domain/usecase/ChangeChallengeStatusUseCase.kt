package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.ChallengeRepository
import javax.inject.Inject

class ChangeChallengeStatusUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val getMyUidUseCase: GetMyUidUseCase
) {
    suspend operator fun invoke(challengeId: Int, status: String): Result<Unit> {
        val myId = getMyUidUseCase()
        return challengeRepository.changeChallengeStatus(challengeId, status, myId.getOrNull()?.toInt() ?: -1)
    }

}