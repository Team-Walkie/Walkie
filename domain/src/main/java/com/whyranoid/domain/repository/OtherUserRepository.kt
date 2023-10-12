package com.whyranoid.domain.repository

import androidx.paging.Pager
import com.whyranoid.domain.model.user.User

interface OtherUserRepository {

    fun searchUsers(query: String): Pager<Int, User>
}