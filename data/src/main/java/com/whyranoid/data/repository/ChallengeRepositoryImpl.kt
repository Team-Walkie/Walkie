package com.whyranoid.data.repository

import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.repository.ChallengeRepository

class ChallengeRepositoryImpl(
    private val challengeDataSource: ChallengeDataSource
): ChallengeRepository {
    override suspend fun getNewChallengePreviews(): List<ChallengePreview> {
        return challengeDataSource.getNewChallengePreviews()
    }

    override suspend fun getChallengingPreviews(): List<ChallengePreview> {
        return challengeDataSource.getChallengingPreviews()
    }
}