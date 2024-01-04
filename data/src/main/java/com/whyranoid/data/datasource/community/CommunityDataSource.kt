package com.whyranoid.data.datasource.community

interface CommunityDataSource {

    suspend fun likePost(postId: Long, likerId: Long): Result<Long>
}