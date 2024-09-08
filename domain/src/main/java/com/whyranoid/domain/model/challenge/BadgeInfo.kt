package com.whyranoid.domain.model.challenge

import androidx.annotation.DrawableRes

data class BadgeInfo (
    val id: Int,
    @DrawableRes val image: Int,
    val name: String,
    val receivedAt: String = ""
)