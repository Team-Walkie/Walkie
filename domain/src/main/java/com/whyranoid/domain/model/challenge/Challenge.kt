package com.whyranoid.domain.model.challenge

import com.whyranoid.domain.model.user.User

data class Challenge(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val contents: String,
    val period: Int,
    val challengeType: ChallengeType,
    val badge: Badge,
    val participantCount: Int,
    val participants: List<User>,
    val process: Int?,
) {
    companion object {

        val DUMMY = Challenge(
            id = 1,
            imageUrl = "https://picsum.photos/250/250",
            title = "챌린지 제목",
            contents = "챌린지 내용",
            period = 7,
            challengeType = ChallengeType.A,
            badge = Badge(
                id = 1,
                name = "뱃지 이름",
                imageUrl = "https://picsum.photos/250/250",
            ),

            participantCount = 10,
            participants = emptyList(),
            process = 50,
        )
    }
}
