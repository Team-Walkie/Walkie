package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

interface PostDataSource {
    suspend fun getPostPreviews(uid: Long): Result<List<PostPreview>>

    suspend fun getMyPostPreviews(uid: Long): Result<List<PostPreview>>

    suspend fun getPostPreviews(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    suspend fun getMyPostPreviews(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    suspend fun getPost(postId: Long): Result<Post>

    suspend fun uploadPost(
        uid: Long,
        content: String,
        colorMode: Int,
        history: String,
        imagePath: String,
    ): Result<String>

    suspend fun getMyFollowingsPost(uid: Long): Result<List<Post>>
}
