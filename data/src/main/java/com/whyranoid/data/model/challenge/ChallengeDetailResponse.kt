package com.whyranoid.data.model.challenge

import com.whyranoid.domain.model.challenge.ChallengeType

data class ChallengeDetailResponse(
    val challenge: ChallengeFromServer,
    val walkies: List<Walkie>
) {
    fun toChallenge(): com.whyranoid.domain.model.challenge.Challenge {
        val participants = walkies.map { it.toUser() }
        return com.whyranoid.domain.model.challenge.Challenge(
            badge = challenge.badge.toBadge(),
            calorie = challenge.calorie,
            challengeType = ChallengeType.getChallengeTypeByString(challenge.category),
            id = challenge.challengeId.toLong(),
            contents = challenge.content,
            imageUrl = challenge.img,
            title = challenge.name,
            process = challenge.progress,
            participants = participants,
            participantCount = participants.size,
            period = challenge.period,
            distance = challenge.distance,
            endTime = challenge.endTime,
            startTime = challenge.startTime,
            status = challenge.status
        )
    }
}