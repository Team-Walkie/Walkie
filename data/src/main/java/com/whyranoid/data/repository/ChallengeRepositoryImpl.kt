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
    override suspend fun getNewChallengePreviews(uid: Int): Result<List<ChallengePreview>> {
        return challengeDataSource.getNewChallengePreviews(uid)
    }

    override suspend fun getChallengingPreviews(uid: Int): Result<List<ChallengePreview>> {
        return challengeDataSource.getChallengingPreviews(uid)
    }

    override suspend fun getTopRankChallengePreviews(): Result<List<ChallengePreview>> {
        return challengeDataSource.getTopRankChallengePreviews()
    }

    override suspend fun getChallengeDetail(uid: Int, challengeId: Long): Challenge {
        return challengeDataSource.getChallengeDetail(uid, challengeId)
    }

    override suspend fun getChallengePreviewsByType(uid: Int, type: ChallengeType): List<ChallengePreview> {
        return challengeDataSource.getChallengePreviewsByType(uid, type)
    }

    override suspend fun getUserBadges(uid: Long): Result<List<Badge>> {
        return challengeDataSource.getUserBadges(uid)
    }

    override suspend fun startChallenge(uid: Int, challengeId: Int): Result<Unit> {
        return challengeDataSource.startChallenge(uid, challengeId)
    }
}
