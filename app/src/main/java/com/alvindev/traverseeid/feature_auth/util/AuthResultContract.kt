package com.alvindev.traverseeid.feature_auth.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import androidx.activity.result.contract.ActivityResultContract

class AuthResultContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent =
        getGoogleSignInClient(context).signInIntent.putExtra("input", input)

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        Log.d("AuthResultContract", "parseResult: $resultCode")
        return when (resultCode) {
            Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
            else -> null
        }
    }
}