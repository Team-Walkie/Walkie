package com.whyranoid.domain.model.challenge

import com.whyranoid.domain.model.user.User

data class Challenge(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val contents: String,
    val period: Int? = null,
    val challengeType: ChallengeType,
    val badge: Badge,
    val participantCount: Int,
    val participants: List<User>,
    val process: Int? = null,
    val calorie: Int? = null,
    val distance: Int? = null,
    val endTime: String,
    val startTime: String,
    val status: String? = null
)
