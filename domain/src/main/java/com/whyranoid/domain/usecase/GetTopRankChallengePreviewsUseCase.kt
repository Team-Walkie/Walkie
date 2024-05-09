package com.whyranoid.domain.usecase

import com.whyranoid.domain.repository.ChallengeRepository

class GetTopRankChallengePreviewsUseCase(
    private val challengeRepository: ChallengeRepository
) {

    suspend operator fun invoke() =
        challengeRepository.getTopRankChallengePreviews()
}