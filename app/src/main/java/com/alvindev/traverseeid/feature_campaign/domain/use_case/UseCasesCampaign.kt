package com.alvindev.traverseeid.feature_campaign.domain.use_case

data class UseCasesCampaign(
    val getCampaignCategories: GetCampaignCategories,
    val getAllCampaigns: GetAllCampaigns,
    val getCampaignById: GetCampaignById,
    val getFirstPageRegisteredCampaigns: GetFirstPageRegisteredCampaigns,
    val getCampaignDetails: GetCampaignDetails,
    val getCampaignParticipants: GetCampaignParticipants,
    val registerCampaign: RegisterCampaign,
    val submitCampaign: SubmitCampaign,
    val getCampaignLocations: GetCampaignLocations
)