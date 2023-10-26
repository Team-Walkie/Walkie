package com.whyranoid.domain.model.post

data class PostPreview(
    val id: Long,
    val imageUrl: String,
    val date: Long = 0L,
    val textVisibleState: TextVisibleState = TextVisibleState.HIDE,
    val distanceText: String = "",
    val timeText: String = "",
    val paceText: String = "",
) {
    companion object {
        val DUMMY_LIST = listOf(
            PostPreview(
                id = 0L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                id = 1L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                id = 2L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                id = 3L,
                imageUrl = "https://picsum.photos/250/250",
            ),
            PostPreview(
                id = 4L,
                imageUrl = "https://picsum.photos/250/250",
            ),
        )
    }
}

enum class TextVisibleState {
    WHITE, BLACK, HIDE
}
