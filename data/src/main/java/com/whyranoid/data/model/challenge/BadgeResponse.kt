package com.whyranoid.data.model.challenge

data class BadgeResponse(
    val badgeId: Int,
    val badgeImg: String,
    val badgeFailureImg: String? = null,
    val badgeName: String,
    val receivedAt: String,
    val isRep: Boolean,
    val walkieId: Int,
    val badgeIdList: List<Int>,
) {
    fun toBadge(): com.whyranoid.domain.model.challenge.Badge {
        return com.whyranoid.domain.model.challenge.Badge(
            id = badgeId.toLong(),
            name = badgeName,
            isRepresentative = isRep,
            imageUrl = badgeImg,
            failureImageUrl = badgeFailureImg
        )
    }
}