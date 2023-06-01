package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.feature_campaign.domain.use_case.UseCasesCampaign
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignListViewModel @Inject constructor(
    private val useCases: UseCasesCampaign
): ViewModel() {
    var state by mutableStateOf(CampaignListState())
    private set

    fun setStatus(status: String?) {
        state = state.copy(status = status)
    }

    fun setCategoryId(categoryId: Int) {
        state = state.copy(categoryId = categoryId)
    }

    fun getAllCampaigns(status: String?) = useCases.getAllCampaigns(status).cachedIn(viewModelScope)

    fun getCampaignsByCategory(categoryId: Int, status: String?) = useCases.getCampaignsByCategory(categoryId, status).cachedIn(viewModelScope)
}
