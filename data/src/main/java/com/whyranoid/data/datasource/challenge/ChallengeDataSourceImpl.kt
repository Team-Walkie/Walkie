package com.whyranoid.data.datasource.challenge

import com.whyranoid.data.getResult
import com.whyranoid.data.model.challenge.request.ChallengeChangeStatusRequest
import com.whyranoid.data.model.challenge.request.ChallengeStartRequest
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
                list.map { it.toChallengePreview() }
            }
        }
    }

    override suspend fun getTopRankChallengePreviews(): Result<List<ChallengePreview>> {
        return kotlin.runCatching {
            val response = challengeService.getTopRankChallenges()
            response.getResult { list ->
                list.map { it.toChallengePreview() }
            }
        }
    }

    override suspend fun getChallengeDetail(uid: Int, challengeId: Long): Challenge {
        return challengeService.getChallengeDetail(challengeId, uid).getResult { it.toChallenge() }
    }

    // TODO 서버 내려갔을 때 앱이 죽지 않도록 예외처리만 했음, 추후 수정 필요
    override suspend fun getChallengePreviewsByType(
        uid: Int,
        type: ChallengeType
    ): List<ChallengePreview> {
        kotlin.runCatching {
            return challengeService.getChallengePreviewsByType(uid, type.serverString)
            .getResult { list ->
                list.map { it.toChallengePreview() }
            }
        }
        return emptyList()
    }

    override suspend fun getUserBadges(uid: Long): Result<List<Badge>> {
        return runCatching {
            challengeService.getBadgeList(uid)
                .getResult { badgeList ->
                    badgeList.map { it.toBadge() }
                }
        }
    }

    override suspend fun startChallenge(uid: Int, challengeId: Int): Result<Unit> {
        return runCatching {
            challengeService.startChallenge(ChallengeStartRequest(uid, challengeId))
        }
    }

    override suspend fun changeChallengeStatus(challengeId: Int, status: String, walkieId: Int): Result<Unit> {
        return runCatching {
            challengeService.changeChallengeStatus(ChallengeChangeStatusRequest(challengeId, status, walkieId))
        }
    }
}
