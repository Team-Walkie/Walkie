package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

interface ChallengeDataSource {

    suspend fun getNewChallengePreviews(uid: Int): Result<List<ChallengePreview>>

    suspend fun getChallengingPreviews(uid: Int): Result<List<ChallengePreview>>

    suspend fun getTopRankChallengePreviews(): Result<List<ChallengePreview>>

    suspend fun getChallengeDetail(uid: Int, challengeId: Long): Challenge

    suspend fun getChallengePreviewsByType(uid: Int, type: ChallengeType): List<ChallengePreview>

    suspend fun getUserBadges(uid: Long): Result<List<Badge>>
}
