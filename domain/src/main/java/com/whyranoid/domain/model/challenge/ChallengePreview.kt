package com.whyranoid.domain.model.challenge

data class ChallengePreview(
    val id: Long,
    val title: String,
    val progress: Float?,
    val type: ChallengeType
) {
    // TODO: Delete
    companion object {

        private val DUMMY_NAME_LIST = listOf(
            "햄버거 세트 불태우기",
            "햄버거 세트 불태우기\n" + "햄버거 세트 불태우기",
            "아침 공복 러닝 일주일 챌린지\n" + "아침에 공복으로 달려",
            "석촌호수 한바퀴\n" + "동네 한바퀴",
            "한강 한바퀴",
            "석촌호수 한바퀴\n" + "동네 한바퀴" + "학교 한바퀴\n",
            "석촌호수 한바퀴\n" + "동네 한바퀴\n" + "학교 한바퀴\n" + "근처 한바퀴\n",
        )

        private val DUMMY_PROGRESS_LIST = listOf(
            0f,
            0.1f,
            0.2f,
            0.3f,
            0.4f,
            0.5f,
            0.6f,
            0.7f,
            0.8f,
            0.9f,
            1f,
        )

        private val DUMMY_ID_LIST = (0..10000L).toList()

        val DUMMY
            get() = ChallengePreview(
                id = DUMMY_ID_LIST.shuffled().first(),
                title = DUMMY_NAME_LIST.shuffled().first(),
                progress = DUMMY_PROGRESS_LIST.shuffled().first(),
                type = ChallengeType.values().toList().shuffled().first(),
            )
    }
}
