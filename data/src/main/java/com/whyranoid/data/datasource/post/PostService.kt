package com.whyranoid.data.datasource.post

import com.whyranoid.data.API
import com.whyranoid.data.model.post.CommentResponse
import com.whyranoid.data.model.post.PostResponse
import com.whyranoid.data.model.post.UploadPostResponse
import com.whyranoid.domain.model.post.Comment
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @GET(API.LIST_UP_MY_POST)
    suspend fun myPosts(
        @Query("walkieId") uid: Long,
    ): Response<List<PostResponse>>

    @GET(API.LIST_UP_POST)
    suspend fun getPosts(
        @Query("walkieId") uid: Long,
    ): Response<List<PostResponse>>

    @GET(API.LIST_UP_COMMENT)
    suspend fun getComments(
        @Query("postId") postId: Long,
    ): Response<List<CommentResponse>>

    @POST(API.WRITE_COMMENT)
    suspend fun sendComment(
        @Body comment: Comment,
    ): Response<Comment>
}
