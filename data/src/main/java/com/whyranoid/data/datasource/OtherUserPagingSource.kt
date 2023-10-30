package com.whyranoid.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.whyranoid.domain.model.user.User

class OtherUserPagingSource(
    private val dataSource: FakeFoundUserApi = FakeFoundUserApi,
) : PagingSource<Int, User>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, User> {
        return try {
            val pageNumber = params.key ?: 1
            val response = dataSource.searchUsers(pageNumber)
            LoadResult.Page(
                data = response.data,
                prevKey = null, // Only paging forward.
                nextKey = pageNumber + PAGE_SIZE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}

object FakeFoundUserApi {
    private val DUMMY = (0..1000).map {
        User.DUMMY.copy(
            uid = it.toLong(),
            name = "유저 $it",
            nickname = "유저 $it",
        )
    }

    fun searchUsers(
        page: Int
    ): UserPageData {
        return UserPageData(
            data = DUMMY.subList(page, page + 10),
        )
    }


}

data class UserPageData(
    val data: List<User>,
)