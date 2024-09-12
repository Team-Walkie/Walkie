package com.whyranoid.domain.repository

import com.whyranoid.domain.model.post.Comment
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview

interface PostRepository {

    /**
     * 유저의 팔로잉들이 작성한 글을 가져옴
     *
     * @param uid
     * @return
     */
    suspend fun getUserPostPreviews(uid: Long): Result<List<PostPreview>>


    /**
     * 해당 유저가 작성한 글을 가져옴
     *
     * @param uid
     * @return
     */
    suspend fun getMyPostPreviews(uid: Long): Result<List<PostPreview>>

    /**
     * 유저의 팔로잉들이 작성한 글을 가져옴
     *
     * @param uid
     * @return
     */
    suspend fun getUserPostPreviews(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    /**
     * 해당 유저가 작성한 글을 가져옴
     *
     * @param uid
     * @return
     */
    suspend fun getMyPostPreviews(
        uid: Long,
        year: Int,
        month: Int,
        day: Int,
    ): Result<List<PostPreview>>

    /**
     * 해당 유저가 작성한 글을 가져옴
     *
     * @param uid
     * @return
     */
    suspend fun getMyPosts(
        uid: Long,
        myUid: Long
    ): Result<List<Post>>

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
