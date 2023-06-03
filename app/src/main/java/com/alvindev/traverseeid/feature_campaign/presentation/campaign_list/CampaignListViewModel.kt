package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignParams
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignLocationEntity
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

    fun setStatus(status: String?) {
        state = state.copy(status = status)
    }

    fun setLocationId(locationId: Int?) {
        state = state.copy(locationId = locationId)
    }

    fun setCategoryId(categoryId: Int) {
        state = state.copy(categoryId = categoryId)
    }

    fun setSearch(search: String?) {
        state = state.copy(search = search)
    }

    fun setIsRegistered(isRegistered: Boolean?) {
        state = state.copy(isRegistered = isRegistered)
    }

    fun getAllCampaigns() = useCases.getAllCampaigns(
        CampaignParams(
            status = state.status,
            locationId = state.locationId,
            isRegistered = state.isRegistered,
            search = state.search
        )
    ).cachedIn(viewModelScope)

    fun getCampaignsByCategory(categoryId: Int) = useCases.getCampaignsByCategory(
        categoryId,
        CampaignParams(
            status = state.status,
            locationId = state.locationId,
            isRegistered = state.isRegistered,
            search = state.search
        )
    ).cachedIn(viewModelScope)

    private fun getCampaignLocations() = viewModelScope.launch {
        useCases.getCampaignLocations().asFlow().collect {
            state = when (it) {
                is ResultState.Success -> {
                    val allLocation = CampaignLocationEntity(
                        0,
                        resourcesProvider.getString(com.alvindev.traverseeid.R.string.Indonesia)
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
