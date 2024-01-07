package com.whyranoid.domain.repository

interface CommunityRepository {

    suspend fun likePost(postId: Long, likerId: Long): Result<Long>
}