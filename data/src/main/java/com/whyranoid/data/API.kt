package com.whyranoid.data

object API {
    const val BASE_URL = "https://walkie-tsvdh.run.goorm.site/"

    const val CHECK_NICKNAME = "api/walkies/signup/check"

    const val SIGN_UP = "api/walkies/signup"

    const val FOLLOW = "api/follow/follow"

    const val WALKING_FOLLOWING = "api/follow/{uid}/walking-followings"

    const val FOLLOWINGS = "api/follow/{uid}/following"

    const val FOLLOWERS = "api/follow/{uid}/follower"

    const val UNFOLLOW = "api/follow/unfollow"

    const val UPLOAD_POST = "api/community/upload-post"

    const val LIST_UP_MY_POST = "api/walkies/listup-my-post"

    const val LIST_UP_POST = "api/community/listup-post"

    const val LIST_UP_COMMENT = "api/community/listup-comment"

    const val SEARCH = "api/community/search-nickname"

    const val SEND_LIKE = "api/community/send-like"

    const val WRITE_COMMENT = "api/community/write-comment"

    const val LOGIN = "api/walkies/login"

    const val NEW_CHALLENGE = "/api/challenge/challenges/new"

    const val PROGRESSING_CHALLENGE = "api/challenge/challenges/progress"

    const val TOP_RANK_CHALLENGE = "api/challenge/challenges/top-rank"

    const val CHALLENGE_CATEGORY = "api/challenge/challenges/category"

    const val CHALLENGE_DETAIL = "api/challenge/challenge-detail"

    const val CHALLENGE_START = "api/challenge/challenge-detail/start"

    object WalkingControl {
        const val RUNNING_START = "api/walk/start"

        const val RUNNING_FINISH = "api/walk/save"

        const val SEND_LIKE = "api/walk/send-like"

        const val LIKE_TOTAL = "api/walk/count-total"

        const val LIKE_COUNT = "api/walk/count-like"

        const val MY = "/api/walkies/my"
    }
}
