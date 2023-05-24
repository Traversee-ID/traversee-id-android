package com.alvindev.traverseeid.feature_campaign.presentation.campaign_user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.presentation.component.MyCampaignCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.CampaignUser
)
@Composable
fun CampaignUserScreen(
    navigator: DestinationsNavigator
){
    LazyColumn{
        items(10){ index ->
            MyCampaignCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    navigator.navigate(ScreenRoute.CampaignDetails)
                },
                status = if(index % 2 == 1) "Ended" else "Ongoing",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignUserScreenPreview(){
  TraverseeTheme {
    CampaignUserScreen(
        navigator = EmptyDestinationsNavigator
    )
  }
}
