package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("hasDuplicatedName") val isDuplicated: Boolean,
    @SerializedName("walkieId") val walkieId: String? = "empty",
    @SerializedName("nickname") val nickName: String? = "empty",
)
