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
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alvindev.destinations.CampaignParticipantsScreenDestination
import com.alvindev.destinations.ForumPostScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.core.util.digitSeparator
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignParticipantItem
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignStatusConstant
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignParticipantEntity
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignRowIcon
import com.alvindev.traverseeid.feature_campaign.util.CampaignUtil
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCampaignEntity
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
    val state = viewModel.state
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    LaunchedEffect(Unit) {
        if(state.campaign == null){
            campaignItem?.let {
                viewModel.setCampaignItem(it)
            }
        }
    }

    if (state.isShowDialog) {
        val title = if (state.isRegistered) {
            stringResource(id = R.string.submit)
        } else {
            stringResource(id = R.string.register_campaign)
        }
        val text = if (state.isRegistered) {
            stringResource(id = R.string.submit_message)
        } else {
            stringResource(id = R.string.register_campaign_message)
        }
        TraverseeAlertDialog(
            title = title,
            text = text,
            onConfirm = if (state.isRegistered) {
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
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
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

                Column(
                    modifier = Modifier
                        .padding(top = screenWidth, bottom = 96.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = state.campaign?.categoryName ?: "",
                            style = Typography.subtitle2.copy(color = MaterialTheme.colors.secondaryVariant)
                        )

                        Text(
                            text = "${state.campaign?.startDate} - ${state.campaign?.endDate}",
                            style = Typography.subtitle2.copy(color = MaterialTheme.colors.secondary)
                        )
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = state.campaign?.name ?: "",
                        style = Typography.h1.copy(color = MaterialTheme.colors.secondaryVariant)
                    )
                    TraverseeRowIcon(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        icon = Icons.Outlined.Place,
                        text = state.campaign?.locationName ?: "",
                    )
                    Row(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.iniated_by) + ":",
                            style = Typography.subtitle2,
                            color = TraverseeBlack
                        )
                        Text(
                            text = state.campaignDetails?.initiatorName ?: "",
                            style = Typography.subtitle2,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            color = TraverseeBlack
                        )
                    }

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
                        Spacer(modifier = Modifier.height(24.dp))
                        AboutCampaign(it)
                    }

                    state.campaign?.endDate?.let { endDate ->
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CampaignRowIcon(
                                modifier = Modifier.padding(end = 16.dp),
                                icon = R.drawable.ic_participants,
                                title = state.campaign.totalParticipants.digitSeparator(),
                                description = stringResource(id = R.string.participants),
                            )
                            CampaignRowIcon(
                                icon = R.drawable.ic_timer,
                                title = if (CampaignUtil.calculateDaysLeft(endDate) == 0) stringResource(
                                    id = R.string.ended
                                ) else CampaignUtil.calculateDaysLeft(endDate).digitSeparator(),
                                description = stringResource(id = R.string.days_left),
                            )
                        }
                    }

                    state.campaignDetails?.terms?.let {
                        Spacer(modifier = Modifier.height(24.dp))
                        CampaignTermsAndConditions(it)
                    }
                    state.campaignDetails?.mission?.let {
                        Spacer(modifier = Modifier.height(24.dp))
                        CampaignMissions(it)
                    }
                    if (state.isRegistered && state.campaign?.status?.lowercase() != CampaignStatusConstant.COMING_SOON_VALUE) {
                        Spacer(modifier = Modifier.height(24.dp))
                        CampaignSubmission(
                            value = state.submissionUrl,
                            onValueChange = viewModel::onSubmissionUrlChanged,
                            enabled = state.campaignUserCondition < CampaignParticipantConstant.REGISTERED_AND_SUBMITTED,
                        )
                    }
                }
            }

            Footer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(color = Color.White)
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                enabledButton = state.enabledButton,
                textButton = state.textButton,
                onClickButton = {
                    viewModel.setShowDialog(true)
                },
                onClickShare = {
                    state.campaign?.let {
                        val forumPostArg = ForumCampaignEntity(
                            id = it.id,
                            name = it.name ?: "-",
                            imageUrl = it.imageUrl,
                            category = it.categoryName ?: "-",
                        )
                        navigator.navigate(ForumPostScreenDestination(campaign = forumPostArg))
                    }
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
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(2.dp, shape = Shapes.large)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(bottom = 8.dp),
            title = stringResource(id = R.string.terms_and_conditions)
        )
        Text(
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
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(2.dp, shape = Shapes.large)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(bottom = 8.dp),
            title = stringResource(id = R.string.missions)
        )
        Text(
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
        TraverseeSectionTitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            title = stringResource(id = R.string.submission)
        )
        TraverseeTextField(
            value = value,
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
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            text = stringResource(id = R.string.winners_announcement).uppercase(),
            style = Typography.h1.copy(color = TraverseeOrange),
            textAlign = TextAlign.Center,
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
                .fillMaxHeight(),
            contentPadding = PaddingValues(4.dp),
            onClick = onClickShare,
            shape = Shapes.medium,
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