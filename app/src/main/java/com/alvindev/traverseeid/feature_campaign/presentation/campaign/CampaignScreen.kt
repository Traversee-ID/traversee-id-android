package com.alvindev.traverseeid.feature_campaign.presentation.campaign

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.CampaignCategoryScreenDestination
import com.alvindev.destinations.CampaignDetailsScreenDestination
import com.alvindev.destinations.CampaignListScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeButton
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.MyCampaignCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.entity.CategoryEntity
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Campaign,
)
@Composable
fun CampaignScreen(
    navigator: DestinationsNavigator,
    viewModel: CampaignViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val stringAllCampaigns = stringResource(id = R.string.all_campaigns)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionMyCampaign(
                campaigns = state.myCampaigns ?: listOf(),
                actionOnClick = {
                    navigator.navigate(ScreenRoute.CampaignUser)
                },
                campaignOnClick = {
                    navigator.navigate(CampaignDetailsScreenDestination(campaignItem = it))
                },
                onClickJoin = {
                    navigator.navigate(
                        CampaignListScreenDestination(
                            id = -1,
                            name = stringAllCampaigns,
                        )
                    )
                }
            )
//            SectionCampaignAround(
//                actionOnClick = {
//                    navigator.navigate(CampaignListScreenDestination(name = "Campaign Around You"))
//                },
//                campaignOnClick = {
//                    navigator.navigate(ScreenRoute.CampaignDetails)
//                }
//            )
            state.categories?.let {
                val categoryArrayList = arrayListOf<CategoryEntity>()
                it.forEach { category ->
                    categoryArrayList.add(category)
                }

                SectionDiscoverCampaign(
                    campaignCategories = if (it.size > 5) categoryArrayList.subList(
                        0,
                        5
                    ) else categoryArrayList,
                    actionOnClick = {
                        navigator.navigate(CampaignCategoryScreenDestination(campaignCategories = categoryArrayList))
                    },
                    categoryOnClick = { category ->
                        navigator.navigate(
                            CampaignListScreenDestination(
                                id = category.id,
                                name = category.name
                            )
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun SectionMyCampaign(
    campaigns: List<CampaignItem>,
    actionOnClick: () -> Unit = {},
    campaignOnClick: (campaign: CampaignItem) -> Unit = {},
    onClickJoin: () -> Unit = {}
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
            subtitle = if (campaigns.isNotEmpty()) stringResource(id = R.string.my_campaign_description) else null,
            actionText = if (campaigns.size >= 5) stringResource(id = R.string.see_all) else null,
            actionOnClick = actionOnClick
        )
        if (campaigns.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(campaigns) { item ->
                    MyCampaignCard(
                        modifier = Modifier
                            .width(screenWidth)
                            .padding(vertical = 8.dp),
                        title = item.campaign.name ?: "",
                        startDate = item.campaign.startDate ?: "",
                        endDate = item.campaign.endDate ?: "",
                        participants = item.campaign.totalParticipants ?: 0,
                        status = item.campaign.status ?: "",
                        imageUrl = item.campaign.imageUrl,
                        onClick = { campaignOnClick(item) }
                    )
                }
            }
        } else {
            EmptyMyCampaigns(onClickJoin = onClickJoin)
        }
    }
}

@Composable
fun EmptyMyCampaigns(
    onClickJoin: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 4.dp,
        shape = Shapes.large,
        backgroundColor = Color.White,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_my_campaign),
                contentDescription = stringResource(id = R.string.no_joined_campaign),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                text = stringResource(id = R.string.no_joined_campaign),
                style = Typography.subtitle1,
                textAlign = TextAlign.Center,
            )
            TraverseeButton(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(id = R.string.join_campaign),
                onClick = onClickJoin
            )
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
                    modifier = Modifier
                        .width(screenWidth)
                        .background(color = Color.White, shape = Shapes.medium),
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
    categoryOnClick: (category: CategoryEntity) -> Unit = {},
    campaignCategories: List<CategoryEntity> = emptyList()
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

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
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .height(intrinsicSize = IntrinsicSize.Max)
                .padding(horizontal = 8.dp),
        ) {
            campaignCategories.forEach { category ->
                TraverseeCategoryCard(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .shadow(4.dp, shape = Shapes.large)
                        .fillMaxHeight()
                        .width(screenWidth / 2.8f)
                        .background(Color.White, shape = Shapes.large)
                        .clickable { categoryOnClick(category) },
                    image = category.imageUrl ?: "",
                    contentDescription = category.name ?: "",
                    text = category.name ?: "",
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