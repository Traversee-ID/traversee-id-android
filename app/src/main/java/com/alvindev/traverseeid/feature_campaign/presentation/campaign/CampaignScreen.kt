package com.alvindev.traverseeid.feature_campaign.presentation.campaign

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.destinations.CampaignListScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignCategory
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.MyCampaignCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
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
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionMyCampaign(
            actionOnClick = {
                navigator.navigate(ScreenRoute.CampaignUser)
            },
            campaignOnClick = {
                navigator.navigate(ScreenRoute.CampaignDetails)
            }
        )
        SectionCampaignAround(
            actionOnClick = {
                navigator.navigate(CampaignListScreenDestination(name = "Campaign Around You"))
            },
            campaignOnClick = {
                navigator.navigate(ScreenRoute.CampaignDetails)
            }
        )
        SectionDiscoverCampaign(
            actionOnClick = {
                navigator.navigate(ScreenRoute.CampaignCategory)
            },
            categoryOnClick = {
                navigator.navigate(CampaignListScreenDestination(name = it))
            }
        )
    }
}

@Composable
fun SectionMyCampaign(
    actionOnClick: () -> Unit = {},
    campaignOnClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 64.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.my_campaigns),
            subtitle = stringResource(id = R.string.submit_my_campaign),
            actionText = stringResource(id = R.string.see_all),
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(5) {
                MyCampaignCard(
                    modifier = Modifier.width(screenWidth),
                    onClick = campaignOnClick
                )
            }
        }
    }
}

@Composable
fun SectionCampaignAround(
    actionOnClick: () -> Unit = {},
    campaignOnClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 64.dp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.campaign_around),
            subtitle = stringResource(id = R.string.find_your_campaign),
            actionText = stringResource(id = R.string.see_all),
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
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
                    onClick = campaignOnClick
                )
            }
        }
    }
}

@Composable
fun SectionDiscoverCampaign(
    actionOnClick: () -> Unit = {},
    categoryOnClick: (category: String) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp
    val campaignCategoryList = listOf(
        CampaignCategory(
            id = 1,
            name = stringResource(id = R.string.all_campaigns),
            image = R.drawable.dummy_komodo_island
        ),
        CampaignCategory(
            id = 2,
            name = "Ecotourism",
            image = R.drawable.dummy_kuta_beach
        ),
        CampaignCategory(
            id = 3,
            name = "Religous",
            image = R.drawable.dummy_borobudur
        ),
        CampaignCategory(
            id = 4,
            name = "Ethnic",
            image = R.drawable.dummy_bromo
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.discover_campaign),
            subtitle = stringResource(id = R.string.browse_campaign_category),
            actionText = stringResource(id = R.string.see_all),
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(campaignCategoryList, key = { it.id }) { category ->
                TraverseeCategoryCard(
                    modifier = Modifier
                        .width(screenWidth / 3)
                        .clickable { categoryOnClick(category.name) },
                    image = category.image,
                    contentDescription = category.name,
                    text = category.name,
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