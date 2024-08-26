package com.whyranoid.domain.usecase

import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType
import com.whyranoid.domain.repository.AccountRepository
import com.whyranoid.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.first

class GetChallengePreviewsByTypeUseCase(
    private val challengeRepository: ChallengeRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(type: ChallengeType): List<ChallengePreview> {
        return challengeRepository.getChallengePreviewsByType(
            accountRepository.walkieId.first()?.toInt() ?: -1, type
        )
    }
}