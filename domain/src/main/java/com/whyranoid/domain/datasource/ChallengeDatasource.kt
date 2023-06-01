package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.challenge.Badge
import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

interface ChallengeDataSource {

    suspend fun getNewChallengePreviews(): List<ChallengePreview>

    suspend fun getChallengingPreviews(): List<ChallengePreview>

    suspend fun getChallengeDetail(challengeId: Long): Challenge

    suspend fun getChallengePreviewsByType(type: ChallengeType): List<ChallengePreview>

    suspend fun getUserBadges(uid: String): Result<List<Badge>>
}
