package com.alvindev.traverseeid.feature_campaign.presentation.campaign_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignParams
import com.alvindev.traverseeid.feature_campaign.domain.use_case.UseCasesCampaign
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignUserViewModel @Inject constructor(
    private val useCases: UseCasesCampaign
): ViewModel() {
    fun getRegisteredCampaigns() = useCases.getAllCampaigns(
        CampaignParams(
            isRegistered = true
        )
    ).cachedIn(viewModelScope)
}