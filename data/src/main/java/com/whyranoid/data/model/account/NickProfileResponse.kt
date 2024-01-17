package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName

data class NickProfileResponse(
    @SerializedName("profileImg") val profileImg: String?,
    @SerializedName("nickname") val nickname: String?,
)
