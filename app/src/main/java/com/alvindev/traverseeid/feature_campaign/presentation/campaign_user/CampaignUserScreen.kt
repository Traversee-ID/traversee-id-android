package com.alvindev.traverseeid.feature_campaign.presentation.campaign_user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alvindev.destinations.CampaignDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
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
    navigator: DestinationsNavigator,
    viewModel: CampaignUserViewModel = hiltViewModel()
){
    val campaigns: LazyPagingItems<CampaignItem> = viewModel.getRegisteredCampaigns().collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(campaigns, key = { item -> item.campaign.id }) { item ->
            MyCampaignCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                title = item?.campaign?.name ?: "",
                startDate = item?.campaign?.startDate ?: "",
                endDate = item?.campaign?.endDate ?: "",
                participants = item?.campaign?.totalParticipants ?: 0,
                status = item?.campaign?.status ?: "",
                imageUrl = item?.campaign?.imageUrl,
                onClick = {
                    navigator.navigate(CampaignDetailsScreenDestination(campaignItem = item))
                }
            )
        }

        when (campaigns.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item{
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_occurred),
                            style = Typography.body2,
                            color = Color.Red,
                        )
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else -> {
                if (campaigns.itemCount == 0) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = stringResource(id = R.string.no_data),
                                style = Typography.body2,
                                color = Color.Red,
                            )
                        }
                    }
                }
            }
        }

        when (campaigns.loadState.append) {
            is LoadState.Error -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_occurred),
                            style = Typography.body2,
                            color = Color.Red,
                        )
                    }
                }
            }
            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else -> {}
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
