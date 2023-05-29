package com.alvindev.traverseeid.feature_settings.presentation.favorite_tourism

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.FavoriteTourism,
)
@Composable
fun FavoriteTourismScreen(
    navigator: DestinationsNavigator
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(10){
            TourismCard(
                modifier = Modifier
                    .padding()
                    .clickable {
                        navigator.navigate(ScreenRoute.TourismDetails)
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteTourismScreenPreview() {
    FavoriteTourismScreen(
        navigator = EmptyDestinationsNavigator
    )
}