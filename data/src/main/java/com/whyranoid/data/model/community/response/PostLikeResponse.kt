package com.whyranoid.data.model.community.response

import com.google.gson.annotations.SerializedName

data class PostLikeResponse(
    @SerializedName("likerCount") val likerCount: Long,
    @SerializedName("likerId") val likerId: Long,
    @SerializedName("likerProfiles") val likerProfiles: List<LikerProfile>,
    @SerializedName("postId") val postId: Long
)