package com.whyranoid.data.datasource.post

import com.whyranoid.data.API
import com.whyranoid.data.model.post.UploadPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostService {
    @Multipart
    @POST(API.UPLOAD_POST)
    suspend fun uploadPost(
        @Part("walkieId") walkieId: RequestBody,
        @Part("content") content: RequestBody,
        @Part("colorMode") colorMode: RequestBody,
        @Part("historyContent") historyContent: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<UploadPostResponse>
}
