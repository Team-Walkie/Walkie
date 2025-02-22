package com.whyranoid.data.model.challenge

import com.whyranoid.domain.model.challenge.Badge

data class BadgeResponse(
    val badgeId: Long,
    val badgeImg: String,
    val badgeFailureImg: String? = null,
    val badgeName: String,
    val receivedAt: String,
    val isRep: Boolean,
    val walkieId: Long,
    val badgeIdList: List<Long>? = null,
) {
    fun toBadge(): Badge {
        return Badge(
            id = badgeId,
            imageUrl = badgeImg,
            failureImageUrl = badgeFailureImg,
            name = badgeName,
            isRepresentative = isRep,
        )
    }
}