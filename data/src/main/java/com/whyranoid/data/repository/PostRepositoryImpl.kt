package com.whyranoid.data.repository

import com.whyranoid.domain.datasource.PostDataSource
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.repository.PostRepository

class PostRepositoryImpl(
    private val postDataSource: PostDataSource,
) : PostRepository {
    override suspend fun getUserPostPreviews(uid: String): Result<List<PostPreview>> {
        return postDataSource.getPostPreviews(uid)
    }

    override suspend fun getUserPostPreviews(
        uid: String,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>> {
        return postDataSource.getPostPreviews(uid, year, month, day)
    }

    override suspend fun getPost(postId: Long): Result<Post> {
        return postDataSource.getPost(postId)
    }
}
