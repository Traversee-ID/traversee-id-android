package com.alvindev.traverseeid.feature_campaign.presentation.campaign

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.CategoryCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.MyCampaignCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.SectionTitle
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Campaign,
)
@Composable
fun CampaignScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionMyCampaign()
        SectionCampaignAround()
        SectionDiscoverCampaign(
            actionOnClick = {
                navigator.navigate(ScreenRoute.CampaignCategory)
            }
        )
    }
}

@Composable
fun SectionMyCampaign(
    actionOnClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(
            title = "My Campaign",
            subtitle = "Submit your campaign now!",
            actionText = "See All",
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                MyCampaignCard(
                    modifier = Modifier.width(screenWidth),
                )
            }
        }
    }
}

@Composable
fun SectionCampaignAround(
    actionOnClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(
            title = "Campaigns Around You",
            subtitle = "Find a campaign in your area",
            actionText = "See All",
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                CampaignCard(
                    modifier = Modifier.width(screenWidth),
                    title = "Share your experience at Borobudur",
                    category = "Cultural",
                    startDate = "May 17",
                    endDate = "June 17",
                    place = "Magelang",
                    participants = 1000,
                )
            }
        }
    }
}

@Composable
fun SectionDiscoverCampaign(
    actionOnClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(
            title = "Discover Campaigns",
            subtitle = "Browse campaigns by category",
            actionText = "See All",
            actionOnClick = actionOnClick
        )
        LazyRow {
            items(5) {
                CategoryCard(
                    modifier = Modifier.width(screenWidth / 3),
                    image = R.drawable.dummy_komodo_island,
                    contentDescription = "Komodo island",
                    text = "All Campaign"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCampaignScreen() {
    TraverseeTheme() {
        CampaignScreen(navigator = EmptyDestinationsNavigator)
    }
}