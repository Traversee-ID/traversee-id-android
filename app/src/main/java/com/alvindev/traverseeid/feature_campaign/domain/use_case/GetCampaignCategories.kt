package com.alvindev.traverseeid.feature_campaign.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignCategories(
    private val repository: CampaignRepository
) {
    suspend operator fun invoke(): LiveData<ResultState<List<CategoryEntity>>> {
        return repository.getCategories()
    }
}