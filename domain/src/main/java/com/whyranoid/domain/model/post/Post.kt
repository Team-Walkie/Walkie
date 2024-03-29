package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User

data class Post(
    val id: Long,
    val imageUrl: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val contents: String,
    val author: User,
    val date: Long = 0L,
) {
    companion object {
        val DUMMY = Post(
            id = 0L,
            imageUrl = "https://picsum.photos/250/250",
            likeCount = 3,
            contents = "오늘도 상쾌한 달리기~",
            author = User.DUMMY,
            isLiked = false
        )
    }
}
