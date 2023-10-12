package com.whyranoid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.whyranoid.data.datasource.OtherUserPagingSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.OtherUserRepository

class OtherUserRepositoryImpl(
    private val otherUserPagingSource: OtherUserPagingSource
): OtherUserRepository {
    override fun searchUsers(query: String): Pager<Int, User> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
            )
        ) {
            otherUserPagingSource
        }
    }
}