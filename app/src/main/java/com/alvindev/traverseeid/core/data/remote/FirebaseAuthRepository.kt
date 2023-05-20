package com.alvindev.moneysaver.feature_auth.data

import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository : BaseAuthRepository {
    override suspend fun loginWithEmailPassword(email: String, password: String): FirebaseUser? {
        try {
            val data = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            return data.user
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }

    override suspend fun loginWithGoogle(idToken: String): FirebaseUser? {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val data = Firebase.auth.signInWithCredential(credential).await()
            return data.user
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }

    override suspend fun registerWithEmailPassword(email: String, password: String): FirebaseUser? {
        try {
            val data = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            return data.user
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String) : Boolean {
        try {
            Firebase.auth.sendPasswordResetEmail(email).await()
            return true
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }

    override fun logout() {
        Firebase.auth.signOut()
    }
}