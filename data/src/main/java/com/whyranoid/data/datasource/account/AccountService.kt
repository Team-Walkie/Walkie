package com.whyranoid.data.datasource.account

import com.whyranoid.data.API
import com.whyranoid.data.model.account.ChangeMyInfoResponse
import com.whyranoid.data.model.account.LoginDataResponse
import com.whyranoid.data.model.account.NickCheckResponse
import com.whyranoid.data.model.account.SignUpRequest
import com.whyranoid.data.model.account.SignUpResponse
import com.whyranoid.data.model.account.UserInfoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AccountService {
    @GET(API.CHECK_NICKNAME)
    suspend fun checkNickName(
        @Query("userName") nickName: String,
    ): Response<NickCheckResponse>

    @POST(API.SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    ): Response<SignUpResponse>

    @GET(API.LOGIN)
    suspend fun login(
        @Query("uid") uid: String,
    ): Response<LoginDataResponse>

    @POST(API.WalkingControl.MY)
    suspend fun changeMyInfo(
        @Part("walkieId") id: Long,
        @Part profileImg: MultipartBody.Part?,
        @Part("nickname") nickName: String,
    ): Response<ChangeMyInfoResponse>

    @GET(API.WalkingControl.MY)
    suspend fun getMyInfo(
        @Query("walkieId") id: Long
    ): Response<UserInfoResponse>
}
