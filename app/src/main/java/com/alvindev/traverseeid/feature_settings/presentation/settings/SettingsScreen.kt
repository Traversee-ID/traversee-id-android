package com.alvindev.traverseeid.feature_settings.presentation.settings

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.RoomPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.destinations.EditProfileScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.domain.entity.UserPreference
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeOutlinedButton
import com.alvindev.traverseeid.feature_settings.domain.entity.SettingsButtonInfo
import com.alvindev.traverseeid.feature_settings.presentation.component.ProfileCard
import com.alvindev.traverseeid.feature_settings.presentation.component.SettingsButton
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

    if(state.isLogout){
        navigator.navigate(ScreenRoute.Login) {
            popUpTo(ScreenRoute.Login) {
                inclusive = true
            }
        }
    }

    val listItem = listOf(
        SettingsButtonInfo(
            title = stringResource(id = R.string.my_campaigns),
            icon = Icons.Outlined.Campaign,
            onClick = {
                navigator.navigate(ScreenRoute.CampaignUser)
            }
        ),
        SettingsButtonInfo(
            title = stringResource(id = R.string.favorite_tourism),
            icon = Icons.Filled.FavoriteBorder,
            onClick = {
                navigator.navigate(ScreenRoute.FavoriteTourism)
            }
        ),
        SettingsButtonInfo(
            title = stringResource(id = R.string.my_posts),
            icon = Icons.Outlined.Forum,
            onClick = {
                navigator.navigate(ScreenRoute.ForumUser)
            }
        ),
        SettingsButtonInfo(
            title = stringResource(id = R.string.language),
            icon = Icons.Outlined.Language,
            onClick = {
                navigator.navigate(ScreenRoute.Language)
            }
        ),
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ProfileCard(
            user = UserPreference(
                name = state.firebaseUser?.displayName,
                email = state.firebaseUser?.email,
                avatarUrl = state.firebaseUser?.photoUrl,
            ),
            actionOnClick = {
                navigator.navigate(
                    EditProfileScreenDestination(
                        name = state.firebaseUser?.displayName,
                        email = state.firebaseUser?.email,
                        avatarUrl = state.firebaseUser?.photoUrl,
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        listItem.forEach { item ->
            SettingsButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = item.title,
                icon = item.icon,
                onClick = item.onClick,
            )
        }

        SettingsButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            text = stringResource(id = R.string.privacy_and_policy),
            onClick = {},
        )

        TraverseeOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            onClick = {
                viewModel.logout()
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
