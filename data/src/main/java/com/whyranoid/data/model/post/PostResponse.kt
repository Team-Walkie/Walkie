package com.whyranoid.data.model.post

import com.google.gson.annotations.SerializedName
import com.whyranoid.data.model.account.UserResponse
import com.whyranoid.domain.model.post.Post
import com.whyranoid.domain.model.post.PostPreview
import com.whyranoid.domain.model.post.TextVisibleState
import com.whyranoid.domain.util.dateFormatter

data class PostResponse(
    @SerializedName("viewerId") val viewerId: Long,
    @SerializedName("poster") val poster: UserResponse,
    @SerializedName("postId") val postId: Long,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("likers") val likers: List<UserResponse>,
    @SerializedName("photo") val photo: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String,
    @SerializedName("colorMode") val colorMode: Long,
    @SerializedName("historyContent") val historyContent: String,
    @SerializedName("commentCount") val commentCount: Long,
) {
    fun toPostPreview(): PostPreview {
        val destructedHistoryContent = historyContent.split('_')
        return PostPreview(
            author = poster.toUser(),
            id = this.postId,
            isLiked = this.liked,
            likers = this.likers.map { it.toUser() },
            imageUrl = this.photo,
            date = dateFormatter.parse(this.date.replace("T", " ")).time,
            textVisibleState = TextVisibleState.values()[this.colorMode.toInt()],
            distanceText = destructedHistoryContent[2],
            timeText = destructedHistoryContent[3],
            paceText = destructedHistoryContent[4],
            address = destructedHistoryContent[1],
            commentCount = commentCount,
        )
    }

    fun toPost(myUid: Long): Post {
        val destructedHistoryContent = historyContent.split('_')
        return Post(
            id = this.postId,
            imageUrl = this.photo,
            likeCount = this.likers.size,
            contents = this.content,
            author = this.poster.toUser(),
            isLiked = this.likers.firstOrNull { it.uid == myUid } != null,
            date = dateFormatter.parse(this.date.replace("T", " ")).time,
            likers = this.likers.map { it.toUser() },
            textVisibleState = TextVisibleState.values()[this.colorMode.toInt()],
            distanceText = destructedHistoryContent[2],
            timeText = destructedHistoryContent[3],
            paceText = destructedHistoryContent[4],
            address = destructedHistoryContent[1],
            commentCount = commentCount,
        )
    }

    private fun String.toRealUrl(): String {
        return (FIREBASE_STORAGE_URL + this.split(DIVIDER)[1] + MEDIA_TYPE).also { println("test\n$it") }
    }

    companion object {
        const val DIVIDER = "/post/"
        const val FIREBASE_STORAGE_URL =
            "https://firebasestorage.googleapis.com/v0/b/walkie-5bfb3.appspot.com/o/post%2F"
        const val MEDIA_TYPE = "?alt=media"
    }
}
