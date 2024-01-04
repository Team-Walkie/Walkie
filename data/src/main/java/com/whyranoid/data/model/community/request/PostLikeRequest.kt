package com.whyranoid.data.model.community.request

import com.google.gson.annotations.SerializedName

data class PostLikeRequest(
    @SerializedName("likerId") val likerId: Long,
    @SerializedName("postId") val postId: Long,
)
