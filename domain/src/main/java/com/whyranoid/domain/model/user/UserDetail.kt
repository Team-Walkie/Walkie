package com.whyranoid.domain.model.user

import com.whyranoid.domain.model.post.PostPreview

data class UserDetail(
    val user: User,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean? = null,
) {
    companion object {
        val DUMMY = UserDetail(
            user = User.DUMMY,
            postCount = PostPreview.DUMMY_LIST.size,
            followerCount = 45,
            followingCount = 30,
            isFollowing = true,
        )
    }
}
