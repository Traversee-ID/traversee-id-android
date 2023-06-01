package com.alvindev.traverseeid.feature_campaign.domain.use_case

data class UseCasesCampaign(
    val getCategories: GetCategories,
    val getAllCampaigns: GetAllCampaigns,
    val getCampaignsByCategory: GetCampaignsByCategory,
    val getRegisteredCampaigns: GetRegisteredCampaigns,
    val getFirstPageRegisteredCampaigns: GetFirstPageRegisteredCampaigns,
    val getCampaignDetails: GetCampaignDetails,
    val getCampaignParticipants: GetCampaignParticipants,
    val registerCampaign: RegisterCampaign,
    val submitCampaign: SubmitCampaign,
    val getCampaignLocations: GetCampaignLocations
)