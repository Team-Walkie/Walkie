package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User

data class PostPreview(
    val author: User,
    val id: Long,
    val isLiked: Boolean = false,
    val likers: List<User> = listOf(),
    val imageUrl: String,
    val date: Long = 0L,
    val textVisibleState: TextVisibleState = TextVisibleState.HIDE,
    val distanceText: String = "",
    val timeText: String = "",
    val paceText: String = "",
    val address: String = "",
    val commentCount: Long = 0,
) {
    companion object {
        val DUMMY_LIST = listOf(
            PostPreview(
                author = User.DUMMY,
                id = 0L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                author = User.DUMMY,
                id = 1L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                author = User.DUMMY,
                id = 2L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                author = User.DUMMY,
                id = 3L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                author = User.DUMMY,
                id = 4L,
                imageUrl = "https://picsum.photos/250/250",
            ),
        )
    }
}

enum class TextVisibleState {
    WHITE, BLACK, HIDE
}
