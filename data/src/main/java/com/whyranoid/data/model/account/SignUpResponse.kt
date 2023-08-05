package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("walkieId") val uid: Long,
    @SerializedName("walkieName") val nickName: String,
)
