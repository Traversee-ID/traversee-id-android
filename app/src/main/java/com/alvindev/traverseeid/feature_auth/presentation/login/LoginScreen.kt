package com.alvindev.traverseeid.feature_auth.presentation.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.TraverseeApplication
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.util.LocaleUtil
import com.alvindev.traverseeid.core.util.isInternetAvailable
import com.alvindev.traverseeid.feature_auth.presentation.component.AuthFormField
import com.alvindev.traverseeid.feature_auth.presentation.component.ErrorMessage
import com.alvindev.traverseeid.feature_auth.presentation.component.GoogleSignInButton
import com.alvindev.traverseeid.feature_auth.presentation.component.PasswordForm
import com.alvindev.traverseeid.feature_auth.util.AuthResultContract
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.google.android.gms.common.api.ApiException
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@RootNavGraph(start = true)
@Destination(
    route = ScreenRoute.Login,
)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val state = viewModel.state
    val signInRequestCode = 1
    val authResultLauncher = rememberLauncherForActivityResult(
        contract = AuthResultContract()
    ) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            Log.d("LoginScreen", "firebaseAuthWithGoogle: ${account?.id}")
            if (account != null) {
                viewModel.loginWithGoogle(account.idToken!!)
            } else {
                viewModel.setErrorMessage("Login failed! please try again")
            }
        } catch (e: ApiException) {
            Log.e("LoginScreen", "signInResult:failed code=${e.statusCode}")
            viewModel.setErrorMessage("Login failed! please try again")
        }
    }

    if (state.firebaseUser != null) {
        LocaleUtil.setLocale(context, TraverseeApplication.LANGUAGE)
        navigator.navigate(ScreenRoute.Campaign)
    } else if (isInternetAvailable(context).not()) {
        TraverseeErrorState(
            image = painterResource(id = R.drawable.empty_error),
            title = stringResource(id = R.string.error_title),
            description = stringResource(id = R.string.error_description),
        )
    } else if (state.isLoading.not()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_image),
                contentDescription = "Traversee Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.h1,
            )
            state.error?.let {
                ErrorMessage(text = it)
            }
            AuthFormField(
                value = state.email,
                onValueChange = viewModel::onEmailChange
            )
            PasswordForm(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                isPasswordVisible = state.isPasswordVisible,
                onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
            )
            TraverseeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = viewModel::login,
                enabled = state.isSubmitting.not(),
                text = state.isSubmitting.not().let {
                    if (it) stringResource(id = R.string.login) else stringResource(id = R.string.logging_in)
                }
            )
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    navigator.navigate(ScreenRoute.ResetPassword)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            TraverseeDivider(
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                text = stringResource(id = R.string.or)
            )
            GoogleSignInButton(
                enabled = state.isSubmitting.not(),
                onClick = {
                    authResultLauncher.launch(signInRequestCode)
                }
            )
            TextButton(
                onClick = {
                    navigator.navigate(ScreenRoute.Register)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.dont_have_account),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TraverseeTheme {
        LoginScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}