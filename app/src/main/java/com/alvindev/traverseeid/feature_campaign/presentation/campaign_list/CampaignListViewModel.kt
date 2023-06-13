package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignParams
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_campaign.domain.use_case.UseCasesCampaign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignListViewModel @Inject constructor(
    private val useCases: UseCasesCampaign,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    var state by mutableStateOf(CampaignListState())
        private set

    init {
        getCampaignLocations()
    }

    fun setFilter(status: String?, locationId: Int?, isRegistered: Boolean?) {
        state = state.copy(status = status, locationId = locationId, isRegistered = isRegistered)
    }

    fun getAllCampaigns(categoryId:Int?, searchQuery: String?) = useCases.getAllCampaigns(
        CampaignParams(
            categoryId=categoryId,
            status = state.status,
            locationId = state.locationId,
            isRegistered = state.isRegistered,
            search = searchQuery
        )
    ).cachedIn(viewModelScope)

    private fun getCampaignLocations() = viewModelScope.launch {
        useCases.getCampaignLocations().asFlow().collect {
            state = when (it) {
                is ResultState.Success -> {
                    val allLocation = LocationEntity(
                        0,
                        resourcesProvider.getString(R.string.Indonesia)
                    )
                    val list = mutableListOf(allLocation)
                    list.addAll(it.data)
                    state.copy(campaignLocations = list)
                }
                else -> state
            }
        }
    }
}
