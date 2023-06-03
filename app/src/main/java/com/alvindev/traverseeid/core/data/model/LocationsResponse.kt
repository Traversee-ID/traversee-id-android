package com.alvindev.traverseeid.core.data.model

import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @field:SerializedName("data")
	val data: List<LocationEntity>? = null,

    @field:SerializedName("message")
	val message: String? = null,
)
