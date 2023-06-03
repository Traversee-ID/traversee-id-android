package com.alvindev.traverseeid.feature_tourism.domain.use_case

data class UseCasesTourism(
    val getTourismCategories: GetTourismCategories,
    val getTourismLocations: GetTourismLocations,
    val getTourisms: GetTourisms,
    val getTourismDetails: GetTourismDetails,
    val postFavoriteTourism: PostFavoriteTourism,
    val deleteFavoriteTourism: DeleteFavoriteTourism
)
