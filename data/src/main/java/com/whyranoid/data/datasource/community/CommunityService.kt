package com.whyranoid.data.datasource.community

import com.whyranoid.data.API
import com.whyranoid.data.model.community.request.PostLikeRequest
import com.whyranoid.data.model.community.response.PostLikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CommunityService {

    @POST(API.SEND_LIKE)
    suspend fun likePost(@Body postLikeRequest: PostLikeRequest): Response<PostLikeResponse>

}