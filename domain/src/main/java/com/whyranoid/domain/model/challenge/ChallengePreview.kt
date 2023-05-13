package com.whyranoid.domain.model.challenge

data class ChallengePreview(
    val id: Long,
    val title: String,
    val badgeImageUrl: String,
    val progress: Float?,
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

        val DUMMY
            get() = ChallengePreview(
                id = 0,
                title = DUMMY_NAME_LIST.shuffled().first(),
                badgeImageUrl = "https://picsum.photos/250/250",
                progress = DUMMY_PROGRESS_LIST.shuffled().first(),
            )
    }
}
