package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.TourismDetailsScreenDestination
import com.alvindev.traverseeid.MainActivity
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.presentation.component.TraverseeDropdown
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.TourismList
)
@Composable
fun TourismListScreen(
    id: Int? = null,
    name: String? = null,
    searchQuery: String? = null,
    navigator: DestinationsNavigator,
    viewModel: TourismListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    name?.let {
        MainActivity.routeName = it
    }

    val lazyGridListState = rememberLazyGridState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            state.canPaginate && ((lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -5) >= (state.tourisms.size - 3))
        }
    }

//    Log.d(
//        "TourismListScreen", "shouldStartPaginate.value: ${state.canPaginate} && ${
//            (lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
//                ?: -5)
//        } >= ${(state.tourisms.size - 3)} = ${shouldStartPaginate.value} == ${state.canPaginate && ((lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
//            ?: -5) >= (state.tourisms.size - 3))}"
//    )
    if (state.canPaginate && ((lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            ?: -5) >= (state.tourisms.size - 3)) && state.listState == ListState.IDLE) {
        viewModel.getAllTourisms(categoryId = id, searchQuery = searchQuery)
    }

    val tourisms = state.tourisms
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp - 200.dp

    LaunchedEffect(lazyGridListState) {
        if (state.lastSeenIndex != 0) {
            lazyGridListState.scrollToItem(state.lastSeenIndex)
        }
    }

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
            state = lazyGridListState,
        ) {
            items(tourisms.size, key = { index -> index }) { index ->
                tourisms[index].let { item ->
                    TourismCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp),
                        title = item.tourism.name ?: "-",
                        category = item.tourism.categoryName ?: "-",
                        city = item.tourism.locationName ?: "-",
                        imageUrl = item.tourism.imageUrl,
                        onClick = {
                            viewModel.setLastSeenIndex(index)
                            navigator.navigate(
                                TourismDetailsScreenDestination(
                                    tourismItem = item
                                )
                            )
                        }
                    )
                }
            }

            item(
                key = state.listState,
                span = { GridItemSpan(2) }
            ) {
                when (state.listState) {
                    ListState.LOADING -> {
                        Column(
                            modifier = Modifier
                                .height(screenHeight)
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    ListState.PAGINATING -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    ListState.ERROR -> {
                        TraverseeErrorState(
                            modifier = Modifier
                                .height(screenHeight)
                                .fillMaxWidth()
                                .padding(16.dp),
                            image = painterResource(id = R.drawable.empty_error),
                            title = stringResource(id = R.string.error_title),
                            description = stringResource(id = R.string.error_description),
                            isCanRetry = true,
                            onRetry = {
                                viewModel.setPage(1)
                                viewModel.getAllTourisms(categoryId = id, searchQuery = searchQuery)
                            }
                        )
                    }
                    else -> {}
                }
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