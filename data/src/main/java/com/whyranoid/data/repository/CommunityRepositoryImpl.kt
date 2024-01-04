package com.whyranoid.data.repository

import com.whyranoid.data.datasource.community.CommunityDataSource
import com.whyranoid.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val communityDataSource: CommunityDataSource
): CommunityRepository {
    override suspend fun likePost(postId: Long, likerId: Long): Result<Long> {
        return communityDataSource.likePost(postId, likerId)
    }
}