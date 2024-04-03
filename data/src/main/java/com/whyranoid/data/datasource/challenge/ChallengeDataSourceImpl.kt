package com.whyranoid.data.datasource.challenge

import com.whyranoid.data.getResult
import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

class ChallengeDataSourceImpl(
    private val challengeService: ChallengeService,
) : ChallengeDataSource {
    override suspend fun getNewChallengePreviews(uid: Int): Result<List<ChallengePreview>> {
        return kotlin.runCatching {
            val response = challengeService.getNewChallenges(uid)
            response.getResult { list ->
                list.map { it.toChallengePreview() }
            }
        }
    }

    override suspend fun getChallengingPreviews(uid: Int): Result<List<ChallengePreview>> {
        return kotlin.runCatching {
            val response = challengeService.getMyProcessingChallenges(uid)
            response.getResult { list ->
                list.map { it.toChallengePreview()}
            }
        }
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
