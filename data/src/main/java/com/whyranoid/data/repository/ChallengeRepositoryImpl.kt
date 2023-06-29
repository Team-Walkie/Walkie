package com.whyranoid.data.repository

import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType
import com.whyranoid.domain.repository.ChallengeRepository

class ChallengeRepositoryImpl(
    private val challengeDataSource: ChallengeDataSource,
) : ChallengeRepository {
    override suspend fun getNewChallengePreviews(): List<ChallengePreview> {
        return challengeDataSource.getNewChallengePreviews()
    }

    override suspend fun getChallengingPreviews(): List<ChallengePreview> {
        return challengeDataSource.getChallengingPreviews()
    }

    override suspend fun getChallengeDetail(challengeId: Long): Challenge {
        return challengeDataSource.getChallengeDetail(challengeId)
    }

    override suspend fun getChallengePreviewsByType(type: ChallengeType): List<ChallengePreview> {
        return challengeDataSource.getChallengePreviewsByType(type)
    }

    override suspend fun getUserBadges(uid: String): Result<List<Badge>> {
        return challengeDataSource.getUserBadges(uid)
    }
}
