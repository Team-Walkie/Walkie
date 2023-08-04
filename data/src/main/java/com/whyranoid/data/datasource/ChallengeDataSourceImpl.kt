package com.whyranoid.data.datasource

import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

class ChallengeDataSourceImpl : ChallengeDataSource {
    // TODO: change to api call
    override suspend fun getNewChallengePreviews(): List<ChallengePreview> {
        return List(10) { ChallengePreview.DUMMY }
    }

    // TODO: change to api call
    override suspend fun getChallengingPreviews(): List<ChallengePreview> {
        return List(10) { ChallengePreview.DUMMY }
    }

    // TODO: change to api call
    override suspend fun getChallengeDetail(challengeId: Long): Challenge {
        return Challenge.DUMMY.copy(
            id = challengeId,
        )
    }

    // TODO: change to api call
    override suspend fun getChallengePreviewsByType(type: ChallengeType): List<ChallengePreview> {
        return List(3) { ChallengePreview.DUMMY }
    }

    // TODO: change to api call
    override suspend fun getUserBadges(uid: Long): Result<List<Badge>> {
        return Result.success(Badge.DUMMY_LIST)
    }
}
