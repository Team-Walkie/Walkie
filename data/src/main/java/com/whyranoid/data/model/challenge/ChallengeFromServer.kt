package com.whyranoid.data.model.challenge

data class ChallengeFromServer(
    val badge: BadgeResponse,
    val category: String,
    val challengeId: Int,
    val content: String,
    val img: String,
    val name: String,
    val progress: Int?,
    val status: String?
)