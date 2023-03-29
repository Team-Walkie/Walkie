package com.whyranoid.domain.model.challenge

data class ChallengePreview(
    val id: Long,
    val title: String,
    val badgeImageUrl: String,
    val progress: Int?,
)
