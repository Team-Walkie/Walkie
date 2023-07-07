package com.whyranoid.domain.repository

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

interface PostRepository {
    suspend fun getUserPostPreviews(uid: String): Result<List<PostPreview>>

    suspend fun getUserPostPreviews(
        uid: String,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    suspend fun getPost(postId: Long): Result<Post>
}
