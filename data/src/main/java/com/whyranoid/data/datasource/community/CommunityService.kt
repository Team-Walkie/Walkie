package com.whyranoid.data.datasource.community

import com.whyranoid.data.API
import com.whyranoid.data.model.account.NickProfileResponse
import com.whyranoid.data.model.community.request.PostLikeRequest
import com.whyranoid.data.model.community.response.PostLikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommunityService {

    @POST(API.SEND_LIKE)
    suspend fun likePost(@Body postLikeRequest: PostLikeRequest): Response<PostLikeResponse>

    @GET(API.WalkingControl.MY)
    suspend fun getUserNickProfile(
        @Query("walkieId") id: Long,
    ): Response<NickProfileResponse>
}
