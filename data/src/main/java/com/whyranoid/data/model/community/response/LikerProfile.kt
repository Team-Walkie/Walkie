package com.whyranoid.data.model.community.response

import com.google.gson.annotations.SerializedName

data class LikerProfile(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("status") val status: String,
    @SerializedName("walkieId") val walkieId: Long
)