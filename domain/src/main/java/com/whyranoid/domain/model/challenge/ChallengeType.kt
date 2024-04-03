package com.whyranoid.domain.model.challenge

// TODO: 기획이 구체화되면 수정
enum class ChallengeType(val text: String) {
    LIFE("라이프"), CALORIE("칼로리"), DISTANCE("거리");

    companion object {
        fun getChallengeTypeByString(type: String): ChallengeType {
            return when (type) {
                "L" -> LIFE
                "C" -> CALORIE
                else -> DISTANCE
            }
        }
    }
}
