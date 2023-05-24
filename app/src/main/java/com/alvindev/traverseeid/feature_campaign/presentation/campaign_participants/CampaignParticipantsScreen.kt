package com.alvindev.traverseeid.feature_campaign.presentation.campaign_participants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.alvindev.traverseeid.feature_campaign.presentation.component.SectionTitle
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = "campaign/participants",
)
@Composable
fun CampaignParticipantsScreen() {
    LazyColumn(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        item {
            CampaignWinners()
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
            CampaignParticipants()
        }
    }
}

@Composable
fun CampaignWinners(){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(
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
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerRank = 1,
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Triseptia",
                winnerPhoto = "",
                winnerRank = 2,
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alphine",
                winnerPhoto = "",
                winnerRank = 3,
                winnerSubmission = "https://www.google.com"
            )
        }
    }
}

@Composable
fun CampaignParticipants(){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle(
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
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerSubmission = "https://www.google.com"
            )
            CampaignWinnerItem(
                winnerName = "Alvin",
                winnerPhoto = "",
                winnerSubmission = "https://www.google.com"
            )
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