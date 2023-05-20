package com.whyranoid.domain.repository

import com.whyranoid.domain.model.challenge.Challenge
import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

interface ChallengeRepository {

    suspend fun getNewChallengePreviews() : List<ChallengePreview>

    suspend fun getChallengingPreviews() : List<ChallengePreview>

    suspend fun getChallengeDetail(challengeId: Long) : Challenge

    suspend fun getChallengePreviewsByType(type: ChallengeType) : List<ChallengePreview>
}