package com.alvindev.traverseeid.feature_settings.domain.use_case

data class UseCasesSettings(
    val logout: Logout,
    val changeLanguage: ChangeLanguage,
    val updateProfile: UpdateProfile,
    val updateProfilePicture: UpdateProfilePicture,
)
