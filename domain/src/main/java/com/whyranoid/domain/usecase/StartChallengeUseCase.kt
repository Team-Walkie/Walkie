package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.ChallengeRepository

class StartChallengeUseCase(
    private val challengeRepository: ChallengeRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(challengeId: Int) =
        challengeRepository.startChallenge(accountRepository.getUID().toInt(), challengeId)
}