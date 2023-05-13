package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.challenge.ChallengePreview

interface ChallengeDataSource {

    suspend fun getNewChallengePreviews() : List<ChallengePreview>

    suspend fun getChallengingPreviews() : List<ChallengePreview>
}