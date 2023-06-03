package com.alvindev.traverseeid.feature_tourism.presentation.tourism

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.TourismListScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.OpenTripEntity
import com.alvindev.traverseeid.feature_tourism.presentation.component.TripCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.Tourism,
)
@Composable
fun TourismScreen(
    navigator: DestinationsNavigator,
    viewModel: TourismViewModel = hiltViewModel()
) {
    val state = viewModel.state

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
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                SectionOpenTrip(
                    modifier = Modifier
                        .fillMaxWidth(),
                    actionOnClick = {
                        navigator.navigate(ScreenRoute.TripList)
                    },
                    tripOnClick = {
                        navigator.navigate(ScreenRoute.TripDetails)
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                if (state.tourismCategories.isNotEmpty()) {
                    val categoryArrayList = arrayListOf<CategoryEntity>()
                    state.tourismCategories.forEach { category ->
                        categoryArrayList.add(category)
                    }

                    SectionDiscoverTourism(
                        actionOnClick = {
                            navigator.navigate(ScreenRoute.TourismPlace)
                        },
                        categoryOnClick = {
                            navigator.navigate(
                                TourismListScreenDestination(
                                    id = it.id,
                                    name = it.name
                                )
                            )
                        },
                        tourismCategories = if (state.tourismCategories.size > 5) categoryArrayList.subList(
                            0,
                            4
                        ) else categoryArrayList,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                TraverseeSectionTitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    title = stringResource(id = R.string.for_you),
                    subtitle = stringResource(id = R.string.tourism_recommendation),
                )
            }
            items(listOf(0, 1, 2, 3).chunked(2)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (i in 0..1) {
                        TourismCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp)
                                .clickable {
                                    navigator.navigate(ScreenRoute.TourismDetails)
                                },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionOpenTrip(
    modifier: Modifier = Modifier,
    actionOnClick: () -> Unit = {},
    tripOnClick: (campaign: OpenTripEntity) -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 64.dp
    val openTrips = listOf(
        OpenTripEntity(
            id = 1,
            title = "Open Trip Bromo",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Bromo"
        ),
        OpenTripEntity(
            id = 2,
            title = "Open Trip Bengkulu",
            price = 4000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 7,
            imageUrl = "https://picsum.photos/200/300",
            location = "Bengkulu"
        ),
        OpenTripEntity(
            id = 3,
            title = "Open Trip Makassar",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Makassar"
        ),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.open_trip),
            subtitle = stringResource(id = R.string.my_trip_subtitle),
            actionText = stringResource(id = R.string.see_all),
            actionOnClick = actionOnClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(openTrips) { item ->
                TripCard(
                    modifier = Modifier
                        .width(screenWidth),
                    title = item.title,
                    duration = item.duration,
                    location = item.location,
                    price = item.price,
                    startDate = item.startDate,
                    endDate = item.endDate,
                    imageUrl = item.imageUrl,
                    onClick = {
                        tripOnClick(item)
                    }
                )
            }
        }
    }
}

@Composable
fun SectionDiscoverTourism(
    actionOnClick: () -> Unit = {},
    categoryOnClick: (category: CategoryEntity) -> Unit = {},
    tourismCategories: List<CategoryEntity> = emptyList()
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.discover_tourism_place),
            subtitle = stringResource(id = R.string.browse_tourism_category),
            actionText = stringResource(id = R.string.see_all),
            actionOnClick = actionOnClick
        )
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .height(intrinsicSize = IntrinsicSize.Max)
                .padding(horizontal = 8.dp),
        ) {
            tourismCategories.forEach { category ->
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
fun SectionDiscoverCampaignPreview() {
    TourismScreen(
        navigator = EmptyDestinationsNavigator
    )
}