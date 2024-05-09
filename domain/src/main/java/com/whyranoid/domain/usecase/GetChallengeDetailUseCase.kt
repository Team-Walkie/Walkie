package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.ChallengeRepository

class GetChallengeDetailUseCase(
    private val accountRepository: AccountRepository,
    private val challengeRepository: ChallengeRepository,
) {

    suspend operator fun invoke(challengeId: Long) = challengeRepository.getChallengeDetail(
        accountRepository.getUID().toInt(), challengeId)
}