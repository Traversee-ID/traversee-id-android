package com.alvindev.traverseeid.feature_settings.data.remote

import com.alvindev.traverseeid.feature_settings.data.model.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface SettingsApi {
    @PUT("profile-pictures")
    @Multipart
    suspend fun updateProfilePicture(
        @Part file: MultipartBody.Part,
    ) : Response<UpdateProfileResponse>
}