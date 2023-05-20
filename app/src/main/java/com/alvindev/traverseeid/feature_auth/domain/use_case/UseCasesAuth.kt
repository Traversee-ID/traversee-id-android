package com.alvindev.traverseeid.feature_auth.domain.use_case

data class UseCasesAuth(
    val loginWithEmailPassword: LoginWithEmailPassword,
    val loginWithGoogle: LoginWithGoogle,
    val sendPasswordReset: SendPasswordReset,
    val registerWithEmailPassword: RegisterWithEmailPassword,
    val getCurrentUser: GetCurrentUser
)