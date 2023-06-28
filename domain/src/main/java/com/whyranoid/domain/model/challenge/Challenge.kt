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
            title = "햄버거 세트 불태우기",
            contents = "햄버거 세트의 평균 칼로리는 1110kcal 에요.\n" +
                    "일주일 동안 걷기로 햄버거 세트 태우기 도전!",
            period = 7,
            challengeType = ChallengeType.values().toList().shuffled().first(),
            badge = Badge(
                id = 1,
                name = "뱃지 이름",
                imageUrl = "https://picsum.photos/250/250",
            ),

            participantCount = 12,
            participants = List(12) { User.DUMMY},
            process = (0..100).random(),
        )
    }
}
