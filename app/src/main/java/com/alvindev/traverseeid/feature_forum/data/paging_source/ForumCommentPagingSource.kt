package com.alvindev.traverseeid.feature_forum.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvindev.traverseeid.feature_forum.data.remote.ForumApi
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity

class ForumCommentPagingSource(
    private val forumApi: ForumApi,
    private val postId: Int
) : PagingSource<Int, ForumCommentEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ForumCommentEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ForumCommentEntity> {
        return try {
            val page = params.key ?: 1
            val response = forumApi.getForumComments(postId, page)

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