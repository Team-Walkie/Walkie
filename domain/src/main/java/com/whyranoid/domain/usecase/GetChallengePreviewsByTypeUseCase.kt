package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType
import com.whyranoid.domain.repository.ChallengeRepository

class GetChallengePreviewsByTypeUseCase(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(type: ChallengeType): List<ChallengePreview> {
        return challengeRepository.getChallengePreviewsByType(type)
    }
}