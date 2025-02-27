package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.repository.ChallengeRepository

class SetUserBadgesUseCase(
    private val challengeRepository: ChallengeRepository,
) {
    suspend operator fun invoke(uid: Long, badges: List<Badge>): Result<Unit> {
        return challengeRepository.setUserBadges(uid, badges)
    }
}