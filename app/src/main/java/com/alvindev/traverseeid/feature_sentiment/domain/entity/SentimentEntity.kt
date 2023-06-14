package com.alvindev.traverseeid.feature_sentiment.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SentimentEntity(
	@field:SerializedName("sentimen")
	val sentimen: Int? = null,

	@field:SerializedName("like")
	val like: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("isi")
	val isi: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable
