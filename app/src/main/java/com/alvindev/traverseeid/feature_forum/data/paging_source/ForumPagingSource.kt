package com.alvindev.traverseeid.feature_forum.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvindev.traverseeid.feature_forum.data.remote.ForumApi
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity

class ForumPagingSource(
    private val forumApi: ForumApi,
) : PagingSource<Int, ForumPostEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ForumPostEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ForumPostEntity> {
        return try {
            val page = params.key ?: 1
            val response = forumApi.getAllForumPosts(page)

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