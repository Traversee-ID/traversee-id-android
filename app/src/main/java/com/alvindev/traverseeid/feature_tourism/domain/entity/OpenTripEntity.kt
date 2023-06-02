package com.alvindev.traverseeid.feature_tourism.domain.entity

data class OpenTripEntity(
    val id: Int,
    val title: String,
    val duration: Int,
    val location: String,
    val price: Int,
    val startDate: String,
    val endDate: String,
    val imageUrl: String,
)
