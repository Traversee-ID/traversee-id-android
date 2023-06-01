package com.alvindev.traverseeid.navigation

import com.alvindev.traverseeid.R

object NavigationMapper {
    fun mapToNavigationStringResource(route: String): Int {
        return when (route) {
            ScreenRoute.Campaign -> R.string.menu_campaign
            ScreenRoute.CampaignCategory -> R.string.campaign_category
            ScreenRoute.CampaignDetails -> R.string.campaign_details
            ScreenRoute.CampaignUser -> R.string.my_campaigns
            ScreenRoute.CampaignParticipants -> R.string.campaign_participants

            ScreenRoute.Forum -> R.string.menu_forum
            ScreenRoute.ForumDetails -> R.string.forum_details
            ScreenRoute.ForumPost -> R.string.forum_post

            ScreenRoute.Tourism -> R.string.menu_tourism
            ScreenRoute.TourismDetails -> R.string.tourism_details
            ScreenRoute.TourismPlace -> R.string.tourism_place

            ScreenRoute.Sentiment -> R.string.menu_sentiment

            ScreenRoute.Settings -> R.string.menu_settings
            ScreenRoute.EditProfile -> R.string.edit_profile
            ScreenRoute.FavoriteTourism -> R.string.favorite_tourism
            ScreenRoute.Language -> R.string.language
            else -> -1
        }
    }
}