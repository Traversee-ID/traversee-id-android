package com.alvindev.traverseeid.feature_campaign.presentation.campaign

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.alvindev.traverseeid.feature_campaign.domain.use_case.UseCasesCampaign
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import kotlinx.coroutines.launch

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val useCases: UseCasesCampaign,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    var state by mutableStateOf(CampaignState())
        private set

    init {
        state = state.copy(
            isLoading = true
        )
        getMyCampaigns()
    }

    fun getMyCampaigns() = viewModelScope.launch{
        useCases.getFirstPageRegisteredCampaigns().asFlow().collect{
            when(it){
                is ResultState.Loading -> {
                    state = state.copy(
                        error = null
                    )
                }
                is ResultState.Success -> {
                    state = state.copy(
                        isLoading = false,
                        error = null,
                        myCampaigns = it.data
                    )
                    getCategories()
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = it.error,
                    )
                }
            }
        }
    }

    private fun getCategories() = viewModelScope.launch{
        useCases.getCampaignCategories().asFlow().collect{
            state = when(it){
                is ResultState.Loading -> {
                    state.copy(
                        categories = null,
                        error = null
                    )
                }
                is ResultState.Success -> {
                    val allCampaigns = CategoryEntity(
                        id = -1,
                        name = resourcesProvider.getString(R.string.all_campaigns),
                        imageUrl = null,
                    )

                    val categories = it.data.toMutableList()
                    categories.add(0, allCampaigns)

                    state.copy(
                        categories = categories,
                        error = null,
                        isLoading = false,
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        categories = null,
                        error = it.error,
                        isLoading = false,
                    )
                }
            }
        }
    }

}