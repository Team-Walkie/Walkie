package com.whyranoid.data.model.challenge

import com.whyranoid.domain.model.challenge.ChallengePreview
import com.whyranoid.domain.model.challenge.ChallengeType

data class ChallengePreviewResponse(
    val category: String,
    val challengeId: Int,
    val name: String,
    val newFlag: Int,
    val progress: Int,
    val status: String
) {
    fun toChallengePreview(): ChallengePreview {
        return ChallengePreview(
            id = challengeId.toLong(),
            title = name,
            progress = progress.toFloat(),
            type = ChallengeType.getChallengeTypeByString(category)
        )
    }
}