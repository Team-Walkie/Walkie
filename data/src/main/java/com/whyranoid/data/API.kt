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

    const val SEARCH = "api/community/search-nickname"

    const val SEND_LIKE = "api/community/send-like"

    const val LOGIN = "api/walkies/login"

    object WalkingControl {
        const val RUNNING_START = "api/walk/start"

        const val RUNNING_FINISH = "api/walk/save"

        const val SEND_LIKE = "api/walk/send-like"

        const val LIKE_TOTAL = "api/walk/count-total"

        const val LIKE_COUNT = "api/walk/count-like"

        const val MY = "/api/walkies/my"
    }
}
