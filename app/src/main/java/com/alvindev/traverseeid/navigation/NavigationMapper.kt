package com.alvindev.traverseeid.navigation

import com.alvindev.traverseeid.R

object NavigationMapper {
    fun mapToNavigationStringResource(route: String): Int {
        return when (route) {
            ScreenRoute.Campaign -> R.string.menu_campaign
            ScreenRoute.CampaignCategory -> R.string.campaign_category
            ScreenRoute.CampaignDetails -> R.string.campaign_details
            ScreenRoute.CampaignUser -> R.string.my_campaigns
            ScreenRoute.Forum -> R.string.menu_forum
            ScreenRoute.Tourism -> R.string.menu_tourism
            ScreenRoute.Sentiment -> R.string.menu_sentiment
            ScreenRoute.Settings -> R.string.menu_settings
            else -> -1
        }
    }
}