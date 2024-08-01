package com.whyranoid.data.model.challenge.request

data class ChallengeChangeStatusRequest(
    val challengeId: Int,
    val status: String,
    val walkieId: Int
)