package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName

data class NickCheckResponse(
    @SerializedName("walkieName") val nickName: String,
    @SerializedName("hasDuplicatedName") val isDuplicated: Boolean,
)
