package com.alvindev.moneysaver.feature_auth.screen.register

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_auth.presentation.component.AuthFormField
import com.alvindev.traverseeid.feature_auth.presentation.component.GoogleSignInButton
import com.alvindev.traverseeid.feature_auth.presentation.component.PasswordForm
import com.alvindev.traverseeid.feature_auth.presentation.register.RegisterViewModel
import com.alvindev.traverseeid.feature_auth.util.AuthResultContract
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.google.android.gms.common.api.ApiException
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Register,
)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state
    val signInRequestCode = 1
    val authResultLauncher = rememberLauncherForActivityResult(
        contract = AuthResultContract()
    ){task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account != null){
                viewModel.loginWithGoogle(account.idToken!!)
            }else{
                viewModel.setErrorMessage(R.string.register_failed)
            }
        } catch (e: ApiException){
            Log.e("RegisterScreen", "signInResult:failed code=${e.statusCode}")
            viewModel.setErrorMessage(R.string.register_failed)
        }
    }

    if (state.firebaseUser != null) {
        navigator.navigate(ScreenRoute.Campaign)
    } else if (state.isLoading.not()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Traversee Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.h1,
            )
            state.error?.let {
                ErrorMessage(text = it)
            }
            AuthFormField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label= stringResource(id = R.string.name),
                placeholder= stringResource(id = R.string.name),
                imageVector = Icons.Outlined.Person,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
            AuthFormField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
            )
            PasswordForm(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                isPasswordVisible = state.isPasswordVisible,
                onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
                labelText = stringResource(id = R.string.password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
            )
            PasswordForm(
                value = state.passswordConfirmation,
                onValueChange = viewModel::onPasswordConfirmationChange,
                isPasswordVisible = state.isPasswordConfirmationVisible,
                onPasswordVisibilityChange = viewModel::onPasswordConfirmationVisibilityChange,
                labelText = stringResource(id = R.string.password_confirmation),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TraverseeButton(
                onClick = viewModel::register,
                enabled = state.isSubmitting.not(),
                text = state.isSubmitting.not().let {
                    if (it) {
                        stringResource(id = R.string.register)
                    } else {
                        stringResource(id = R.string.registering)
                    }
                }
            )
            TraverseeDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "OR"
            )
            GoogleSignInButton(
                text = stringResource(id = R.string.register_with_google),
                enabled = state.isSubmitting.not(),
                onClick = {
                    authResultLauncher.launch(signInRequestCode)
                },
            )
            TextButton(
                onClick = {
                    navigator.popBackStack()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
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
@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier.padding(top = 16.dp),
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.error
        )
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    TraverseeTheme {
        RegisterScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}