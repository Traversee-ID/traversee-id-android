package com.alvindev.traverseeid.core.domain.repository

import com.google.firebase.auth.FirebaseUser

interface BaseAuthRepository {
    suspend fun loginWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun loginWithGoogle(idToken: String): FirebaseUser?

    suspend fun registerWithEmailPassword(email: String, password: String): FirebaseUser?

    fun getCurrentUser(): FirebaseUser?

    suspend fun sendPasswordReset(email: String): Boolean

    fun logout()
}