package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.alvindev.traverseeid.MainActivity
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.FilterBottomSheet
import com.alvindev.traverseeid.feature_campaign.presentation.component.FilterButton
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Destination(
    route = ScreenRoute.CampaignList
)
@Composable
fun CampaignListScreen(
    id: Int = -1,
    name: String?,
    navigator: DestinationsNavigator,
    viewModel: CampaignListViewModel = hiltViewModel()
) {
    name?.let {
        MainActivity.routeName = it
    }
    val isHideButton = name == stringResource(id = R.string.campaign_around)
    val state = viewModel.state


    LaunchedEffect(Unit) {
        if(state.categoryId == -1) {
            viewModel.setCategoryId(id)
        }
    }

    val campaigns: LazyPagingItems<CampaignItem> = if (id != -1) {
        viewModel.getCampaignsByCategory(id, state.status, state.locationId, state.isRegistered).collectAsLazyPagingItems()
    } else {
        viewModel.getAllCampaigns(state.status, state.locationId, state.isRegistered).collectAsLazyPagingItems()
    }

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded },
        skipHalfExpanded = true,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            FilterBottomSheet(
                modifier = Modifier.padding(16.dp),
                onClose = {
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                },
                onApply = { isRegistered, status, location ->
                    coroutineScope.launch {
                        modalSheetState.hide()
                    }
                    viewModel.setStatus(status)
                    viewModel.setLocationId(location)
                    viewModel.setIsRegistered(isRegistered)
                    campaigns.refresh()
                },
                campaignLocations = state.campaignLocations,
                locationSelected = state.locationId,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (isHideButton.not()) {
                FilterButton(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .shadow(2.dp, clip = true, shape = RoundedCornerShape(50))
                        .background(color = Color.White)
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .paddingFromBaseline(4.dp)
                        .padding(4.dp),
                    onClickFilter = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                modalSheetState.show()
                            }
                        }
                    },
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    top = 8.dp,
                    bottom = 16.dp,
                )
            ) {
                items(campaigns, key = { item -> item.campaign.id }) { item ->
                    CampaignCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = Shapes.medium)
                            .padding(vertical = 8.dp),
                        title = item?.campaign?.name ?: "",
                        category = item?.campaign?.categoryName ?: "",
                        startDate = item?.campaign?.startDate ?: "",
                        endDate = item?.campaign?.endDate ?: "",
                        place = item?.campaign?.locationName ?: "",
                        participants = item?.campaign?.totalParticipants ?: 0,
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
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignListScreenPreview() {
    TraverseeTheme {
        CampaignListScreen(
            name = "All Campaigns",
            navigator = EmptyDestinationsNavigator
        )
    }
}

