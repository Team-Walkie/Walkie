package com.whyranoid.data.datasource.community

import com.whyranoid.data.getResult
import com.whyranoid.data.model.community.request.PostLikeRequest

class CommunityDataSourceImpl(
    private val service: CommunityService
) : CommunityDataSource {
    override suspend fun likePost(postId: Long, likerId: Long): Result<Long> {

        return kotlin.runCatching {
            val request = PostLikeRequest(likerId, postId)
            val response = service.likePost(request)

            response.getResult {
                it.likerCount
            }
        }
    }
}