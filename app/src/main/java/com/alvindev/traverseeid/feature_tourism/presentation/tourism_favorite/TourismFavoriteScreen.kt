package com.alvindev.traverseeid.feature_tourism.presentation.tourism_favorite

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.TourismDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.EmptyResultRecipient
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination(
    route = ScreenRoute.FavoriteTourism,
)
@Composable
fun TourismFavoriteScreen(
    navigator: DestinationsNavigator,
    viewModel: TourismFavoriteViewModel = hiltViewModel(),
    resultRecipient: ResultRecipient<TourismDetailsScreenDestination, Boolean>
) {
    val state = viewModel.state
    val lazyGridListState = rememberLazyGridState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            state.canPaginate && (lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -5) >= (state.tourisms.size - 5)
        }
    }
    val tourisms = state.tourisms
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp - 100.dp

    if (shouldStartPaginate.value && state.listState == ListState.IDLE) {
        viewModel.getFavoriteTourisms()
    }

    LaunchedEffect(lazyGridListState) {
        if (state.lastSeenIndex != 0) {
            lazyGridListState.scrollToItem(state.lastSeenIndex)
        }
    }

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // `GoToProfileConfirmationDestination` was shown but it was canceled
                // and no value was set (example: dialog/bottom sheet dismissed)
            }
            is NavResult.Value -> {
                val value = result.value
                if(value == true){
                    viewModel.deleteFavoriteTourism(state.lastSeenIndex)
                }
            }
        }
    }

    LazyVerticalGrid(
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState(),
    ) {
        items(tourisms.size, key = { index -> index }) { index ->
            tourisms[index].let { item ->
                TourismCard(
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
                            viewModel.getFavoriteTourisms()
                        }
                    )
                }
                else -> {
                    if (tourisms.isEmpty()) {
                        TraverseeErrorState(
                            modifier = Modifier
                                .height(screenHeight)
                                .fillMaxWidth()
                                .padding(16.dp),
                            image = painterResource(id = R.drawable.empty_favorite),
                            title = stringResource(id = R.string.no_favorite_tourism_found),
                            description = stringResource(id = R.string.no_favorite_tourism_description)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteTourismScreenPreview() {
    TourismFavoriteScreen(
        navigator = EmptyDestinationsNavigator,
        resultRecipient = EmptyResultRecipient()
    )
}