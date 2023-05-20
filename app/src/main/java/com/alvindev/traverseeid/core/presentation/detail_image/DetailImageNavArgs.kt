package com.alvindev.traverseeid.core.presentation.detail_image

import android.net.Uri

data class DetailImageNavArgs(
    val imageUri: Uri? = null,
    val imageTitle: String? = null,
    val imageDescription: String? = null,
)