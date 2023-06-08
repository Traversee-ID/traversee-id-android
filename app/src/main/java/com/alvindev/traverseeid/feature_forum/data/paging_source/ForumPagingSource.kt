package com.alvindev.traverseeid.feature_forum.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvindev.traverseeid.feature_forum.data.remote.ForumApi
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem

class ForumPagingSource(
    private val forumApi: ForumApi,
    private val userId: String? = null
) : PagingSource<Int, ForumPostItem>() {
    override fun getRefreshKey(state: PagingState<Int, ForumPostItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ForumPostItem> {
        return try {
            val page = params.key ?: 1
            val response = if(userId == null) {
                forumApi.getAllForumPosts(page)
            } else {
                forumApi.getUserForumPosts(userId, page)
            }

            val data = response.data ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (data.isEmpty() || data.size < 5) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}