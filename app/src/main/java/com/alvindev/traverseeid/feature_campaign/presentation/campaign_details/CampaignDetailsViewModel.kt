package com.alvindev.traverseeid.feature_campaign.presentation.campaign_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignStatusConstant
import com.alvindev.traverseeid.feature_campaign.domain.use_case.UseCasesCampaign
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignDetailsViewModel @Inject constructor(
    private val useCases: UseCasesCampaign,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    var state by mutableStateOf(CampaignDetailsState())
        private set

    fun setCampaignItem(campaignItem: CampaignItem) {
        state = state.copy(
            isLoading = true,
            error = null,
            campaign = campaignItem.campaign,
            isRegistered = campaignItem.isRegistered
        )
        getCampaignDetails(campaignItem.campaign.id)
    }

    fun setCampaignById(campaignId: Int) {
        state = state.copy(
            isLoading = true,
            error = null,
        )
        getCampaignById(campaignId)
    }

    fun setShowDialog(isShow: Boolean) {
        state = state.copy(
            isShowDialog = isShow
        )
    }

    private fun getCampaignDetails(campaignId: Int) = viewModelScope.launch {
        useCases.getCampaignDetails(campaignId).asFlow().collect {
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        campaignDetails = null,
                        submissionUrl = "",
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    getCampaignParticipants(state.campaign?.id!!)
                    when (state.campaign?.status?.lowercase()) {
                        CampaignStatusConstant.COMPLETED -> {
                            state = state.copy(
                                campaignDetails = it.data.campaignDetail,
                                error = null,
                                textButton = resourcesProvider.getString(R.string.campaign_ended),
                                enabledButton = false,
                                campaignUserCondition = CampaignParticipantConstant.ENDED,
                                submissionUrl = it.data.submissionUrl ?: ""
                            )
                        }
                        CampaignStatusConstant.COMING_SOON_VALUE -> {
                            when (state.isRegistered) {
                                true -> {
                                    state = state.copy(
                                        campaignDetails = it.data.campaignDetail,
                                        error = null,
                                        textButton = resourcesProvider.getString(R.string.already_registered),
                                        enabledButton = false,
                                        campaignUserCondition = CampaignParticipantConstant.REGISTERED,
                                    )
                                }
                                else -> {
                                    state = state.copy(
                                        campaignDetails = it.data.campaignDetail,
                                        error = null,
                                        textButton = resourcesProvider.getString(R.string.register),
                                        enabledButton = true,
                                        campaignUserCondition = CampaignParticipantConstant.NOT_REGISTERED
                                    )
                                }
                            }
                        }
                        else -> {
                            if (state.isRegistered) {
                                when (it.data.submissionUrl) {
                                    null -> {
                                        state = state.copy(
                                            campaignDetails = it.data.campaignDetail,
                                            error = null,
                                            textButton = resourcesProvider.getString(R.string.submit),
                                            enabledButton = false,
                                            campaignUserCondition = CampaignParticipantConstant.REGISTERED
                                        )
                                    }
                                    else -> {
                                        state = state.copy(
                                            campaignDetails = it.data.campaignDetail,
                                            error = null,
                                            textButton = resourcesProvider.getString(R.string.already_submitted),
                                            enabledButton = false,
                                            campaignUserCondition = CampaignParticipantConstant.REGISTERED_AND_SUBMITTED,
                                            submissionUrl = it.data.submissionUrl,
                                        )
                                    }
                                }
                            } else {
                                state = state.copy(
                                    campaignDetails = it.data.campaignDetail,
                                    error = null,
                                    textButton = resourcesProvider.getString(R.string.register),
                                    enabledButton = true,
                                    campaignUserCondition = CampaignParticipantConstant.NOT_REGISTERED
                                )
                            }
                        }
                    }
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                        campaignDetails = null,
                        submissionUrl = "",
                        error = it.error,
                        textButton = resourcesProvider.getString(R.string.register),
                        enabledButton = false,
                        campaignUserCondition = CampaignParticipantConstant.NOT_REGISTERED
                    )
                }
            }
        }
    }

    private fun getCampaignById(campaignId: Int) = viewModelScope.launch {
        useCases.getCampaignById(campaignId).asFlow().collect{
            when(it){
                is ResultState.Loading -> {
                    state = state.copy(
                        campaignDetails = null,
                        submissionUrl = "",
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    setCampaignItem(it.data)
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                        campaignDetails = null,
                        submissionUrl = "",
                        error = it.error,
                        textButton = resourcesProvider.getString(R.string.register),
                        enabledButton = false,
                        campaignUserCondition = CampaignParticipantConstant.NOT_REGISTERED
                    )
                }
            }
        }
    }

    private fun getCampaignParticipants(campaignId: Int) = viewModelScope.launch {
        useCases.getCampaignParticipants(campaignId).asFlow().collect {
            state = when (it) {
                is ResultState.Loading -> {
                    state.copy(
                        campaignWinners = null,
                        campaignOtherParticipants = null,
                        error = null
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        campaignWinners = it.data[0].winners,
                        campaignOtherParticipants = it.data[1].otherParticipants,
                        error = null,
                        isLoading = false
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        isLoading = false,
                        campaignWinners = null,
                        campaignOtherParticipants = null,
                        error = it.error
                    )
                }
            }
        }
    }

    fun updateCampaignUserCondition() {
        when (state.campaignUserCondition) {
            CampaignParticipantConstant.NOT_REGISTERED -> {
                state = state.copy(
                    campaignUserCondition = CampaignParticipantConstant.REGISTERED,
                    textButton = if (state.campaign?.status?.lowercase() == CampaignStatusConstant.COMING_SOON_VALUE) "Already Registered" else "Submit",
                    enabledButton = state.campaign?.status?.lowercase() != CampaignStatusConstant.COMING_SOON_VALUE
                )
            }
            CampaignParticipantConstant.REGISTERED -> {
                state = state.copy(
                    campaignUserCondition = CampaignParticipantConstant.REGISTERED_AND_SUBMITTED,
                    textButton = resourcesProvider.getString(R.string.already_submitted),
                )
            }
            CampaignParticipantConstant.REGISTERED_AND_SUBMITTED -> {
                state = state.copy(
                    campaignUserCondition = CampaignParticipantConstant.ENDED,
                    textButton = resourcesProvider.getString(R.string.campaign_ended),
                )
            }
        }
    }

    fun registerCampaign() = viewModelScope.launch {
        useCases.registerCampaign(state.campaign?.id!!).asFlow().collect {
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        isLoadingButton = true,
                        errorButton = null,
                        isShowDialog = false
                    )
                }
                is ResultState.Success -> {
                    state = state.copy(
                        isLoadingButton = false,
                        isRegistered = true,
                        campaignUserCondition = CampaignParticipantConstant.REGISTERED,
                        textButton = if (state.campaign?.status?.lowercase() == CampaignStatusConstant.COMING_SOON_VALUE) "Already Registered" else "Submit",
                        enabledButton = state.campaign?.status?.lowercase() != CampaignStatusConstant.COMING_SOON_VALUE,
                        errorButton = null,
                        isShowDialog = false
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoadingButton = false,
                        errorButton = it.error,
                        isShowDialog = false
                    )
                }
            }
        }
    }

    fun onSubmissionUrlChanged(submissionUrl: String) {
        state = state.copy(
            submissionUrl = submissionUrl,
            enabledButton = submissionUrl.trim().isNotEmpty()
        )
    }

    fun submitCampaign() = viewModelScope.launch {
        useCases.submitCampaign(state.campaign?.id!!, state.submissionUrl).asFlow().collect{
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        isLoadingButton = true,
                        errorButton = null,
                        isShowDialog = false
                    )
                }
                is ResultState.Success -> {
                    state = state.copy(
                        isLoadingButton = false,
                        campaignUserCondition = CampaignParticipantConstant.REGISTERED_AND_SUBMITTED,
                        textButton = resourcesProvider.getString(R.string.already_submitted),
                        enabledButton = false,
                        errorButton = null,
                        isShowDialog = false
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoadingButton = false,
                        errorButton = it.error,
                        isShowDialog = false
                    )
                }
            }
        }
    }
}