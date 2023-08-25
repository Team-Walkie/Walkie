package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName

// TODO : remove default value
data class NickCheckResponse(
    @SerializedName("hasDuplicatedName") val isDuplicated: Boolean,
    @SerializedName("walkieId") val walkieId: String? = "empty",
    @SerializedName("nickname") val nickName: String? = "empty",
)
