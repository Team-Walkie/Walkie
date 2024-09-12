package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.post.Comment
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

interface PostDataSource {

    /**
     *  사용자의 팔로잉들의 글을 가져온다
     *
     * @param uid
     * @return
     */
    suspend fun getPostPreviews(uid: Long): Result<List<PostPreview>>

    /**
     * 해당 사용자가 작성한 글들을 가져온다
     *
     * @param uid
     * @return
     */
    suspend fun getMyPostPreviews(uid: Long): Result<List<PostPreview>>

    /**
     * 해당 사용자가 작성한 글들을 가져온다
     *
     * @param uid
     * @return
     */
    suspend fun getMyPosts(uid: Long, myUid: Long): Result<List<Post>>

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

    suspend fun getEveryPost(uid: Long): Result<List<Post>>

    suspend fun getComments(postId: Long): Result<List<Comment>>

    suspend fun sendComment(
        postId: Long,
        commenterId: Long,
        date: String,
        content: String,
    ): Result<Unit>
}
