package com.alvindev.traverseeid.feature_campaign.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignParams
import com.alvindev.traverseeid.feature_campaign.data.remote.CampaignApi

class CampaignsPagingSource(
    private val campaignApi: CampaignApi,
    val campaignParams: CampaignParams
) : PagingSource<Int, CampaignItem>() {
    override fun getRefreshKey(state: PagingState<Int, CampaignItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CampaignItem> {
        return try {
            val page = params.key ?: 1

            val response = campaignApi.getAllCampaigns(
                    page,
                    campaignParams.categoryId,
                    campaignParams.status,
                    campaignParams.locationId,
                    campaignParams.isRegistered,
                    campaignParams.search
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