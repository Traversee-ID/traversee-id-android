package com.alvindev.traverseeid.feature_campaign.presentation.campaign_details

import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignDetailsEntity
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignEntity
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignParticipantEntity

data class CampaignDetailsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val campaign: CampaignEntity? = null,
    val campaignDetails: CampaignDetailsEntity? = null,
    val isRegistered: Boolean = false,
    val submissionUrl: String = "",
    val campaignWinners: List<CampaignParticipantEntity>? = null,
    val campaignOtherParticipants: List<CampaignParticipantEntity>? = null,
    val enabledButton: Boolean = true,
    val textButton: String = "Register",
    val campaignUserCondition: Int = CampaignParticipantConstant.NOT_REGISTERED,
    val isShowDialog: Boolean = false,
    val errorButton: String? = null,
    val isLoadingButton: Boolean = false,
)
