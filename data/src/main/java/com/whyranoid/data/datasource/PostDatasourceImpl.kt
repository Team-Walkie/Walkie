package com.whyranoid.data.datasource

import com.whyranoid.domain.datasource.PostDatasource
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

class PostDatasourceImpl : PostDatasource {
    // TODO: change to api call
    override suspend fun getPostPreviews(uid: String): Result<List<PostPreview>> {
        return Result.success(PostPreview.DUMMY_LIST)
    }

    // TODO: change to api call
    override suspend fun getPost(postId: Long): Result<Post> {
        return Result.success(Post.DUMMY)
    }
}
