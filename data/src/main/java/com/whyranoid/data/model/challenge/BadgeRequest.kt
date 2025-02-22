package com.whyranoid.data.model.challenge

data class BadgeRequest (
    val walkieId: Long,
    val badgeIdList: List<Long>,
)