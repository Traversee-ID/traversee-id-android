package com.alvindev.traverseeid.feature_tourism.domain.use_case

data class UseCasesTourism(
    val getTourismCategories: GetTourismCategories,
    val getTourismLocations: GetTourismLocations,
    val getTourisms: GetTourisms,
    val getTourismRecommendations: GetTourismRecommendations,
    val getTourismById: GetTourismById,
    val getTourismDetails: GetTourismDetails,
    val postFavoriteTourism: PostFavoriteTourism,
    val deleteFavoriteTourism: DeleteFavoriteTourism,
    val getOpenTrip: GetOpenTrip,
    val getFirstPageOpenTrip: GetFirstPageOpenTrip,
    val getTripDestinations: GetTripDestinations,
)
