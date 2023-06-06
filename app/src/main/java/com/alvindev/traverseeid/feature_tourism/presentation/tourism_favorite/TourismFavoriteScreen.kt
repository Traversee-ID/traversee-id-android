package com.alvindev.traverseeid.feature_tourism.presentation.tourism_favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvindev.destinations.TourismDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.FavoriteTourism,
)
@Composable
fun TourismFavoriteScreen(
    navigator: DestinationsNavigator,
    viewModel: TourismFavoriteViewModel = hiltViewModel()
) {
    val tourisms: LazyPagingItems<TourismItem> =
        viewModel.getFavoriteTourisms().collectAsLazyPagingItems()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp - 100.dp

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
                    TraverseeErrorState(
                        modifier = Modifier.height(screenHeight).fillMaxWidth().padding(16.dp),
                        image = painterResource(id = R.drawable.empty_error),
                        title = stringResource(id = R.string.error_title),
                        description = stringResource(id = R.string.error_description),
                    )
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
                        TraverseeErrorState(
                            modifier = Modifier.height(screenHeight).fillMaxWidth().padding(16.dp),
                            image = painterResource(id = R.drawable.empty_favorite),
                            title = stringResource(id = R.string.no_favorite_tourism_found),
                            description = stringResource(id = R.string.no_favorite_tourism_description)
                        )
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

@Preview(showBackground = true)
@Composable
fun FavoriteTourismScreenPreview() {
    TourismFavoriteScreen(
        navigator = EmptyDestinationsNavigator
    )
}