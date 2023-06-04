package com.alvindev.traverseeid.feature_tourism.presentation.trip_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alvindev.destinations.TripDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_tourism.presentation.component.TripCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = ScreenRoute.TripList,
)
@Composable
fun TripListScreen(
    navigator: DestinationsNavigator,
    viewModel: TripListViewModel = hiltViewModel()
) {

    val openTrips = viewModel.getOpenTrip().collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(openTrips, key = { item -> item.id }) { item ->
            item?.let { it ->
                TripCard(
                    title = it.title ?: "-",
                    duration = it.duration ?: "-",
                    categories = it.categories.joinToString(", "),
                    price = it.price ?: "-",
                    startDate = it.tripStart ?: "-",
                    endDate = it.tripEnd ?: "-",
                    imageUrl= if(it.imagesUrl.isNotEmpty()) it.imagesUrl[0] else null,
                    onClick = {
                        navigator.navigate(TripDetailsScreenDestination(trip = it))
                    }
                )
            }
        }

        when (openTrips.loadState.refresh) { //FIRST LOAD
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
                if (openTrips.itemCount == 0) {
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

        when (openTrips.loadState.append) {
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
