package com.whyranoid.data.datasource.challenge

import com.whyranoid.data.API
import com.whyranoid.data.model.StatusWithMessage
import com.whyranoid.data.model.challenge.BadgeResponse
import com.whyranoid.data.model.challenge.ChallengeDetailResponse
import com.whyranoid.data.model.challenge.ChallengePreviewResponse
import com.whyranoid.data.model.challenge.request.ChallengeStartRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChallengeService {

    @GET(API.NEW_CHALLENGE)
    suspend fun getNewChallenges(
        @Query("walkieId") uid: Int,
    ): Response<List<ChallengePreviewResponse>>

    @GET(API.PROGRESSING_CHALLENGE)
    suspend fun getMyProcessingChallenges(@Query("walkieId") uid: Int): Response<List<ChallengePreviewResponse>>

    @GET(API.TOP_RANK_CHALLENGE)
    suspend fun getTopRankChallenges(): Response<List<ChallengePreviewResponse>>

    @GET(API.CHALLENGE_CATEGORY)
    suspend fun getChallengePreviewsByType(
        @Query("walkieId") uid: Int,
        @Query("category") type: String
    ): Response<List<ChallengePreviewResponse>>

    @GET(API.CHALLENGE_DETAIL)
    suspend fun getChallengeDetail(
        @Query("challengeId") challengeId: Long,
        @Query("walkieId") uid: Int,
    ): Response<ChallengeDetailResponse>

    @POST(API.CHALLENGE_START)
    suspend fun startChallenge(
        @Body challengeStartRequest: ChallengeStartRequest
    ): Response<StatusWithMessage>

    @GET(API.BadgeAPI.BADGES)
    suspend fun getBadgeList(
        @Query("walkieId") uid: Long
    ): Response<List<BadgeResponse>>
}