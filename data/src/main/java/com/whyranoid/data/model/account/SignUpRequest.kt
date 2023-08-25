package com.whyranoid.data.model.account

data class SignUpRequest(
    val userName: String,
    val profileImg: String,
    val authId: String,
    val agreeGps: Boolean,
    val agreeSubscription: Boolean,
)
