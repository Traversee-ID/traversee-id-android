package com.alvindev.traverseeid.navigation

object ScreenRoute {
    // campaign feature
    const val Campaign = "campaign"
    const val CampaignCategory = "campaign/category"
    const val CampaignList = "campaign/list"
    const val CampaignDetails = "campaign/details"
    const val CampaignParticipants = "campaign/participants"
    const val CampaignUser = "campaign/user"

    //forum feature
    const val Forum = "forum"
    const val ForumDetails = "forum/details"
    const val ForumPost= "forum/post"

    //tourism feature
    const val Tourism = "tourism"
    const val TourismDetails = "tourism/details"
    const val TourismCategory = "tourism/category"
    const val TourismList = "tourism/list"
    const val TripList = "tourism/trip_list"
    const val TripDetails = "tourism/trip_details"

    //sentiment feature
    const val Sentiment = "sentiment"

    //profile feature
    const val Settings = "settings"
    const val EditProfile = "settings/edit_profile"
    const val FavoriteTourism = "settings/favorite_tourism"
    const val Language = "settings/language"

    //auth feature
    const val Login = "login"
    const val Register = "register"
    const val ResetPassword = "reset_password"

    //core feature
    const val DetailImage = "detail_image"
}