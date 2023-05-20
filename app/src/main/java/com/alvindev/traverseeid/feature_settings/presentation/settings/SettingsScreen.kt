package com.alvindev.traverseeid.feature_settings.presentation.settings

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeOutlinedButton
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = ScreenRoute.Settings,
)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserImage(
            image = state.firebaseUser?.photoUrl,
            onClick = {
                viewModel.setShowDialog(true)
            }
        )

        Text(
            text = state.firebaseUser?.displayName ?: "",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier.padding(
                top = state.firebaseUser?.displayName?.let { 4.dp }
                    ?: 0.dp
            ),
            text = state.firebaseUser?.email ?: "",
            style = MaterialTheme.typography.body2,
            color = Color.Black,
        )

        TraverseeButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
            },
            text = stringResource(R.string.edit_profile),
            contentPadding = PaddingValues(12.dp),
        )

        Spacer(modifier = Modifier.weight(1.0f)) // fill height with spacer
        TraverseeOutlinedButton(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                viewModel.logout()
                navigator.navigate(ScreenRoute.Login) {
                    popUpTo(ScreenRoute.Login) {
                        inclusive = true
                    }
                }
            },
            text = stringResource(R.string.logout),
            contentPadding = PaddingValues(12.dp),
        )
    }
}


@Composable
fun UserImage(
    image: Uri?,
    onClick: () -> Unit = {},
) {
    TraverseeButton(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(50))
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.2.dp,
            pressedElevation = 0.5.dp,
            disabledElevation = 0.dp
        ),
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),

            ) {
            if (image != null) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(50)),
                    model = image,
                    contentDescription = stringResource(id = R.string.user_avatar),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.user_avatar),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray)
                )
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    imageVector = Icons.Default.Camera,
                    contentDescription = stringResource(id = R.string.edit_avatar),
                )
            }
        }
    }
}
