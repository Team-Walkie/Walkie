package com.whyranoid.domain.model.user

data class User(
    val uid: Long,
    val name: String,
    val nickname: String,
    val imageUrl: String,
) {
    companion object {

        private val DUMMY_NAME_LIST = listOf(
            "김철수",
            "김영희",
            "김영수",
            "김민수",
            "김민희",
            "김민지",
            "김민수",
            "곽두팔",
        )

        private val DUMMY_NICKNAME_LIST = listOf(
            "하늘을 달리는 소년",
            "무지개를 담은 소녀",
            "무지개를 담은 소년",
            "하늘을 달리는 소녀",
            "밥 먹고 싶은 사람",
            "배가 고픈 사람",
            "집 가고 싶은 사람",
        )

        val DUMMY
            get() =
                User(
                    uid = 0L,
                    name = DUMMY_NAME_LIST.shuffled().first(),
                    nickname = DUMMY_NICKNAME_LIST.shuffled().first(),
                    imageUrl = "https://picsum.photos/250/250",
                )
    }
}
