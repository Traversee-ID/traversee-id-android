package com.alvindev.traverseeid.feature_auth.presentation.reset

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_auth.presentation.component.EmailForm
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
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "Enter your email address below and we'll send you a link to reset your password",
            style = MaterialTheme.typography.body1
        )
        state.error?.let {
            ErrorMessage(text = it)
        }
        EmailForm(
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
                if (it) "Reset" else "Loading..."
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