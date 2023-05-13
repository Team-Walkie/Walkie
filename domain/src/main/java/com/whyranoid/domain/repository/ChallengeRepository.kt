package com.whyranoid.domain.repository

import com.whyranoid.domain.model.challenge.ChallengePreview

interface ChallengeRepository {

    suspend fun getNewChallengePreviews() : List<ChallengePreview>

    suspend fun getChallengingPreviews() : List<ChallengePreview>
}