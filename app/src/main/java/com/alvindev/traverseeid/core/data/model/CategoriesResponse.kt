package com.alvindev.traverseeid.core.data.model

import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @field:SerializedName("data")
	val data: List<CategoryEntity>? = null,

    @field:SerializedName("message")
	val message: String? = null
)

