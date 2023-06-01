package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.repository.ChallengeRepository

class GetUserBadgesUseCase(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(uid: String): Result<List<Badge>> {
        return challengeRepository.getUserBadges(uid)
    }
}
