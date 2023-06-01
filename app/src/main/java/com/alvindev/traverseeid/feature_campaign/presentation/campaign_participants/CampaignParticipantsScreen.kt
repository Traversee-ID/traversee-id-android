package com.alvindev.traverseeid.feature_campaign.presentation.campaign_participants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignWinnerItem
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignParticipantEntity
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.CampaignParticipants
)
@Composable
fun CampaignParticipantsScreen(
    campaignWinners: ArrayList<CampaignParticipantEntity> = arrayListOf(),
    campaignOtherParticipants: ArrayList<CampaignParticipantEntity> = arrayListOf()
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            CampaignWinners(
                campaignWinners = campaignWinners
            )
        }
        item{
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 4.dp
            )
        }
        item{
            if(campaignOtherParticipants.isNotEmpty()){
                CampaignParticipants(
                    campaignOtherParticipants = campaignOtherParticipants
                )
            }
        }
    }
}

@Composable
fun CampaignWinners(
    campaignWinners: List<CampaignParticipantEntity>
){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TraverseeSectionTitle(
            title = "Campaign Winners"
        )
        Column(
            modifier = Modifier
                .shadow(2.dp, shape = Shapes.large)
                .fillMaxWidth()
                .background(color = Color.White, shape = Shapes.large)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            campaignWinners.forEach { winner ->
                CampaignWinnerItem(
                    winnerName = winner.userDisplayName ?: "-",
                    winnerPhoto = winner.userProfileImage,
                    winnerSubmission = winner.submissionUrl ?: "",
                    winnerRank = winner.position ?: -1,
                )
            }
        }
    }
}

@Composable
fun CampaignParticipants(
    campaignOtherParticipants: List<CampaignParticipantEntity>
){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TraverseeSectionTitle(
            title = "Other Participants"
        )
        Column(
            modifier = Modifier
                .shadow(2.dp, shape = Shapes.large)
                .fillMaxWidth()
                .background(color = Color.White, shape = Shapes.large)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            campaignOtherParticipants.forEach { participant ->
                CampaignWinnerItem(
                    winnerName = participant.userId ?: "",
                    winnerPhoto = "",
                    winnerSubmission = participant.submissionUrl ?: ""
                )
            }
        }
    }
}

@Preview
@Composable
fun CampaignParticipantsScreenPreview() {
    TraverseeTheme() {
        CampaignParticipantsScreen()
    }
}