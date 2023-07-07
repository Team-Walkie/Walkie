package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

interface PostDataSource {
    suspend fun getPostPreviews(uid: String): Result<List<PostPreview>>

    suspend fun getPostPreviews(
        uid: String,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    suspend fun getPost(postId: Long): Result<Post>
}
