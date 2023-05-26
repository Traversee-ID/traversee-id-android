package com.alvindev.traverseeid.feature_tourism.presentation.tourism_place

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.destinations.CampaignListScreenDestination
import com.alvindev.destinations.TourismListScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismPlace
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.TourismPlace
)
@Composable
fun TourismPlaceScreen(
    navigator: DestinationsNavigator
) {
    val tourismPlaces = listOf(
        TourismPlace(
            id = 1,
            name = "All Places",
            image = R.drawable.dummy_komodo_island
        ),
        TourismPlace(
            id = 2,
            name = "Marine",
            image = R.drawable.dummy_kuta_beach
        ),
        TourismPlace(
            id = 3,
            name = "Religous",
            image = R.drawable.dummy_borobudur
        ),
        TourismPlace(
            id = 4,
            name = "Mountain",
            image = R.drawable.dummy_bromo
        ),
        TourismPlace(
            id = 5,
            name = "Culinary",
            image = R.drawable.dummy_kuta_beach
        ),
        TourismPlace(
            id = 6,
            name = "Historical",
            image = R.drawable.dummy_komodo_island
        ),
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(tourismPlaces, key = { place -> place.id }) { place ->
                TraverseeCategoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navigator.navigate(TourismListScreenDestination(name = place.name))
                        },
                    image = place.image,
                    contentDescription = place.name,
                    text = place.name,
                    isFullSize = true,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TourismPlaceScreenPreview() {
    TraverseeTheme() {
        TourismPlaceScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}