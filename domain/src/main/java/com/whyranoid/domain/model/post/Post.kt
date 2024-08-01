package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User
import java.io.Serializable

data class Post(
    val id: Long,
    val imageUrl: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val contents: String,
    val author: User,
    val date: Long = 0L,

    val likers: List<User> = listOf(),
    val textVisibleState: TextVisibleState = TextVisibleState.HIDE,
    val distanceText: String = "",
    val timeText: String = "",
    val paceText: String = "",
    val address: String = "",
) : Serializable {
    companion object {
        val DUMMY =
            Post(
                id = 0L,
                imageUrl = "https://picsum.photos/250/250",
                likeCount = 3,
                contents = "오늘도 상쾌한 달리기~",
                author = User.DUMMY,
                isLiked = false,
            )
    }
}

fun Post.toPostPreview(): PostPreview {
    return PostPreview(
        this.author,
        this.id,
        this.isLiked,
        this.likers,
        this.imageUrl,
        this.date,
        this.textVisibleState,
        this.distanceText,
        this.timeText,
        this.paceText,
        this.address,
    )
}
