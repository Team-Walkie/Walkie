package com.whyranoid.data.datasource.follow

import com.whyranoid.data.API
import com.whyranoid.data.model.account.UserResponse
import com.whyranoid.data.model.follow.FollowRequest
import com.whyranoid.data.model.follow.FollowResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowService {

    @POST(API.FOLLOW)
    suspend fun follow(
        @Body followRequest: FollowRequest,
    ): Response<FollowResponse>

    @GET(API.WALKING_FOLLOWING)
    suspend fun getWalkingFollowings(
        @Path("uid") uid: Long,
    ): Response<List<UserResponse>>

    @GET(API.FOLLOWINGS)
    suspend fun getFollowings(
        @Path("uid") uid: Long,
    ): Response<List<UserResponse>>

    @GET(API.FOLLOWERS)
    suspend fun getFollowers(
        @Path("uid") uid: Long,
    ): Response<List<UserResponse>>

    @DELETE(API.UNFOLLOW)
    suspend fun unfollow(
        @Body followRequest: FollowRequest,
    ): Response<FollowResponse>

    @GET(API.SEARCH)
    suspend fun searchNickname(
        @Query("keyword") keyword: String,
    ): Response<List<UserResponse>>
}
