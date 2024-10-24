package com.whyranoid.data.model.challenge

data class ChallengeFromServer(
    val badge: BadgeResponse,
    val calorie: Int?,
    val category: String,
    val challengeId: Int,
    val content: String,
    val distance: Int?,
    val endTime: String,
    val img: String,
    val name: String,
    val period: Int?,
    val progress: Int,
    val startTime: String,
    val status: String
)