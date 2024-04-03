package com.whyranoid.data.datasource.challenge

import com.whyranoid.data.API
import com.whyranoid.data.model.challenge.ChallengePreviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChallengeService {

    @GET(API.NEW_CHALLENGE)
    suspend fun getNewChallenges(
        @Query("walkieId") uid: Int,
    ): Response<List<ChallengePreviewResponse>>
}