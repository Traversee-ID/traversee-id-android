package com.alvindev.traverseeid.feature_auth.presentation.reset

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_auth.presentation.component.AuthFormField
import com.alvindev.traverseeid.feature_auth.presentation.component.ErrorMessage
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.ResetPassword,
)
@Composable
fun ResetScreen(
    viewModel: ResetViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state
    val context = LocalContext.current

    state.isSuccess.let {
        if (it) {
            navigator.popBackStack()
            Toast.makeText(
                context,
                "Reset password link has been sent to your email",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            text = stringResource(id = R.string.reset_password),
            style = MaterialTheme.typography.h1,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
            text = stringResource(id = R.string.reset_password_description),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
        state.error?.let {
            ErrorMessage(text = it)
        }
        AuthFormField(
            value = state.email,
            onValueChange = viewModel::onEmailChange
        )
        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = viewModel::reset,
            enabled = state.isLoading.not(),
            text = state.isLoading.not().let {
                if (it) stringResource(id = R.string.send_email_verification) else stringResource(id = R.string.loading)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResetScreenPreview() {
    TraverseeTheme {
        ResetScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}