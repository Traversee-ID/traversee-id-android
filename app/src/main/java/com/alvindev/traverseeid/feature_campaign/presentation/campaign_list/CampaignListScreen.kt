package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.MainActivity
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.presentation.component.CampaignCard
import com.alvindev.traverseeid.feature_campaign.presentation.component.FilterBottomSheet
import com.alvindev.traverseeid.feature_campaign.presentation.component.FilterSortButton
import com.alvindev.traverseeid.feature_campaign.presentation.component.SortBottomSheet
import com.alvindev.traverseeid.navigation.ScreenRoute
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
    navigator: DestinationsNavigator
) {
    name?.let {
        MainActivity.routeName = it
    }
    val isHideButton = name == "Campaign Around You"

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
    var sheetType by rememberSaveable { mutableStateOf("sort") }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            if (sheetType == "sort") {
                SortBottomSheet(
                    modifier = Modifier.padding(16.dp),
                    onClose = {
                        coroutineScope.launch {
                            modalSheetState.hide()
                        }
                    }
                )
            } else {
                FilterBottomSheet(
                    modifier = Modifier.padding(16.dp),
                    onClose = {
                        coroutineScope.launch {
                            modalSheetState.hide()
                        }
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 8.dp, bottom = if (isHideButton) 8.dp else 56.dp)
            ) {
                items(10) {
                    CampaignCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        title = "Share your experience at Borobudur",
                        category = "Cultural",
                        startDate = "May 17",
                        endDate = "June 17",
                        place = "Magelang",
                        participants = 1000,
                        onClick = {
                            navigator.navigate(ScreenRoute.CampaignDetails)
                        }
                    )
                }
            }

            if (isHideButton.not()) {
                FilterSortButton(
                    modifier = Modifier
                        .shadow(2.dp, clip = true, shape = RoundedCornerShape(50))
                        .background(color = Color.White)
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .align(Alignment.BottomCenter)
                        .paddingFromBaseline(8.dp)
                        .padding(8.dp),
                    onClickFilter = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                sheetType = "filter"
                                modalSheetState.show()
                            }
                        }
                    },
                    onClickSort = {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                sheetType = "sort"
                                modalSheetState.show()
                            }
                        }
                    }
                )
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

