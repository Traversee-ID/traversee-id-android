package com.alvindev.traverseeid.feature_campaign.presentation.campaign_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.destinations.CampaignParticipantsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignDescriptionCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignParticipantItem
import com.alvindev.traverseeid.core.theme.TraverseeGreen
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignStatusConstant
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignParticipantEntity
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.CampaignDetails,
)
@Composable
fun CampaignDetailsScreen(
    campaignItem: CampaignItem? = null,
    navigator: DestinationsNavigator,
    viewModel: CampaignDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        campaignItem?.let {
            viewModel.setCampaignItem(campaignItem)
        }
    }
    val state = viewModel.state
    val context = LocalContext.current

    if (state.isShowDialog) {
        val title = if(state.isRegistered) {
            stringResource(id = R.string.submit)
        } else {
            stringResource(id = R.string.register_campaign)
        }
        val text = if(state.isRegistered) {
            stringResource(id = R.string.submit_message)
        } else {
            stringResource(id = R.string.register_campaign_message)
        }
        TraverseeAlertDialog(
            title = title,
            text = text,
            onConfirm = if(state.isRegistered) {
                {
                    viewModel.submitCampaign()
                }
            } else {
                {
                    viewModel.registerCampaign()
                }
            },
            onCancel = {
                viewModel.setShowDialog(false)
            }
        )
    }

    if (state.errorButton != null) {
        Toast.makeText(context, state.errorButton, Toast.LENGTH_SHORT).show()
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.error,
                style = Typography.body2,
                color = Color.Red,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 96.dp),
            ) {
                Box(modifier = Modifier.aspectRatio(1f)) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = state.campaign?.imageUrl ?: "",
                        contentDescription = state.campaign?.name ?: "",
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )

                    if (state.campaignUserCondition >= CampaignParticipantConstant.REGISTERED_AND_SUBMITTED) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .size(24.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(50))
                                .padding(2.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(id = R.string.campaign_ended),
                            tint = TraverseeGreen,
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = state.campaign?.name ?: "",
                    style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
                )
                CampaignDescriptionCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .shadow(2.dp, shape = Shapes.large)
                        .clip(Shapes.large)
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    iniatedBy = state.campaignDetails?.initiatorName ?: "",
                    startDate = state.campaign?.startDate ?: "",
                    endDate = state.campaign?.endDate ?: "",
                    category = state.campaign?.categoryName ?: "",
                    participants = state.campaign?.totalParticipants ?: 0,
                )
                state.campaignWinners?.let { list ->
                    if (list.isNotEmpty()) {
                        val campaignWinners = arrayListOf<CampaignParticipantEntity>()
                        val campaignOtherParticipants = arrayListOf<CampaignParticipantEntity>()

                        list.forEach { campaignParticipant ->
                            campaignWinners.add(campaignParticipant)
                        }

                        state.campaignOtherParticipants?.let { campaignParticipants ->
                            campaignParticipants.forEach { campaignParticipant ->
                                campaignOtherParticipants.add(campaignParticipant)
                            }
                        }

                        CampaignWinners(
                            campaignWinners = list,
                            onClickAllParticipants = {
                                navigator.navigate(
                                    CampaignParticipantsScreenDestination(
                                        campaignWinners = campaignWinners,
                                        campaignOtherParticipants = campaignOtherParticipants
                                    )
                                )
                            }
                        )
                    }
                }
                state.campaignDetails?.description?.let {
                    AboutCampaign(it)
                }
                state.campaignDetails?.terms?.let {
                    CampaignTermsAndConditions(it)
                }
                state.campaignDetails?.mission?.let {
                    CampaignMissions(it)
                }
                if (state.isRegistered && state.campaign?.status?.lowercase() != CampaignStatusConstant.COMING_SOON_VALUE) {
                    CampaignSubmission(
                        value = state.submissionUrl,
                        onValueChange = viewModel::onSubmissionUrlChanged,
                        enabled = state.campaignUserCondition < CampaignParticipantConstant.REGISTERED_AND_SUBMITTED,
                    )
                }
            }

            Footer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(color = Color.White)
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                enabledButton = state.enabledButton,
                textButton = state.textButton,
                onClickButton = {
                    viewModel.setShowDialog(true)
                },
                onClickShare = {
                    navigator.navigate(ScreenRoute.ForumPost)
                }
            )
        }
    }
}

@Composable
fun AboutCampaign(
    description: String
) {
    Column {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        TraverseeSectionTitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            title = stringResource(id = R.string.about_campaign)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = description,
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun CampaignTermsAndConditions(
    terms: String
) {
    Column {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        TraverseeSectionTitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            title = stringResource(id = R.string.terms_and_conditions)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = terms,
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun CampaignMissions(
    missions: String
) {
    Column {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        TraverseeSectionTitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            title = stringResource(id = R.string.missions)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = missions,
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun CampaignSubmission(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {
    Column {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        TraverseeSectionTitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            title = "Submission"
        )
        TraverseeTextField(
            value= value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text("Attach Proof Link")
            },
            placeholder = {
                Text("https://instagram.com/...")
            },
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )
    }
}

@Composable
fun CampaignWinners(
    campaignWinners: List<CampaignParticipantEntity>,
    onClickAllParticipants: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.winners_announcement),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = stringResource(id = R.string.winners_announcement_description),
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shadow(2.dp, shape = Shapes.large)
                .fillMaxWidth()
                .background(color = Color.White, shape = Shapes.large)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            campaignWinners.forEach { winner ->
                CampaignParticipantItem(
                    winnerName = winner.userDisplayName ?: "-",
                    winnerPhoto = winner.userProfileImage,
                    winnerSubmission = winner.submissionUrl ?: "",
                    winnerRank = winner.position ?: -1,
                )
            }
            TextButton(
                onClick = onClickAllParticipants
            ) {
                Text(
                    text = stringResource(id = R.string.see_all_participants),
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    textButton: String,
    enabledButton: Boolean,
    onClickButton: () -> Unit,
    onClickShare: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeOutlinedButton(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Max),
            contentPadding = PaddingValues(4.dp),
            onClick = onClickShare,
            shape = Shapes.large,
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
            )
        }
        TraverseeButton(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            text = textButton,
            onClick = onClickButton,
            enabled = enabledButton,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignDetailsScreenPreview() {
    TraverseeTheme() {
        CampaignDetailsScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}