package com.whyranoid.data.datasource

import com.whyranoid.domain.datasource.ChallengeDataSource
import com.whyranoid.domain.model.challenge.ChallengePreview
import kotlinx.coroutines.delay

class ChallengeDataSourceImpl: ChallengeDataSource {
    // TODO: change to api call
    override suspend fun getNewChallengePreviews(): List<ChallengePreview> {
        delay(1500)
        return List(10) {ChallengePreview.DUMMY}
    }

    // TODO: change to api call
    override suspend fun getChallengingPreviews(): List<ChallengePreview> {
        delay(500)
        return List(10) {ChallengePreview.DUMMY}
    }
}