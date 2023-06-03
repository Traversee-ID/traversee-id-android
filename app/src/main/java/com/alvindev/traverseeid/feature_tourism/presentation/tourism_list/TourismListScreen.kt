package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvindev.destinations.TourismDetailsScreenDestination
import com.alvindev.traverseeid.MainActivity
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeDropdown
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Destination(
    route = ScreenRoute.TourismList
)
@Composable
fun TourismListScreen(
    id: Int = -1,
    name: String? = null,
    navigator: DestinationsNavigator,
    viewModel: TourismListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    name?.let {
        MainActivity.routeName = it
    }

    val tourisms: LazyPagingItems<TourismItem> =
        viewModel.getAllTourisms(id).collectAsLazyPagingItems()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp - 100.dp

    Column {
        TraverseeDropdown(
            dropdownMenuItems = state.tourismLocations.map {
                it.name
            },
            selectedItem = state.locationName ?: stringResource(id = R.string.Indonesia),
            onSelectedItemChange = viewModel::setLocation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        LazyVerticalGrid(
            contentPadding = PaddingValues(16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(tourisms.itemCount, key = { index -> index }) { index ->
                tourisms[index]?.let { item ->
                    TourismCard(
                        title = item.tourism.name ?: "-",
                        category = item.tourism.categoryName ?: "-",
                        city = item.tourism.locationName ?: "-",
                        imageUrl = item.tourism.imageUrl,
                        onClick = {
                            navigator.navigate(
                                TourismDetailsScreenDestination(
                                    tourismItem = item
                                )
                            )
                        }
                    )
                }
            }

            when (tourisms.loadState.refresh) { //FIRST LOAD
                is LoadState.Error -> {
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Box(
                            modifier = Modifier.height(screenHeight).fillMaxWidth().padding(16.dp),
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
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Column(
                            modifier = Modifier.height(screenHeight).fillMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                else -> {
                    if (tourisms.itemCount == 0) {
                        item(
                            span = { GridItemSpan(2) }
                        ) {
                            Box(
                                modifier = Modifier.height(screenHeight).fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
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

            when (tourisms.loadState.append) {
                is LoadState.Error -> {
                    item(
                        span = { GridItemSpan(2) }
                    ) {
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
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
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

@Preview(showBackground = true)
@Composable
fun PreviewTourismListScreen() {
    TourismListScreen(
        navigator = EmptyDestinationsNavigator
    )
}