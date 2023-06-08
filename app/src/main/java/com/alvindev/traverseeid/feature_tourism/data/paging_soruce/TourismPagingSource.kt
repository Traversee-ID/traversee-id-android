package com.alvindev.traverseeid.feature_tourism.data.paging_soruce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.data.remote.TourismApi
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem

class TourismPagingSource(
    private val tourismApi: TourismApi,
    private val tourismParams: TourismParams
) : PagingSource<Int, TourismItem>() {
    override fun getRefreshKey(state: PagingState<Int, TourismItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TourismItem> {
        return try {
            val page = params.key ?: 1
            val response = tourismApi.getTourism(
                page = page,
                categoryId = tourismParams.categoryId,
                isFavorite = tourismParams.isFavorite,
                locationId = tourismParams.locationId,
                search = tourismParams.search,
            )

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