package com.alvindev.traverseeid.feature_auth.domain.mapper

import com.alvindev.traverseeid.core.domain.entity.UserPreference
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.google.firebase.auth.FirebaseUser

fun firebaseUserToUser(firebaseUser: FirebaseUser): User {
    return User(
        email = firebaseUser.email,
        name = firebaseUser.displayName,
        phone = firebaseUser.phoneNumber,
        photo = firebaseUser.photoUrl.toString(),
        uid = firebaseUser.uid
    )
}

fun firebaseUserToUserPreference(firebaseUser: FirebaseUser): UserPreference {
    return UserPreference(
        email = firebaseUser.email,
        name = firebaseUser.displayName,
        avatarUrl = firebaseUser.photoUrl,
        uid = firebaseUser.uid
    )
}