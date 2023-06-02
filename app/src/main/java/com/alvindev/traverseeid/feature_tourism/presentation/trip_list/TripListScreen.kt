package com.alvindev.traverseeid.feature_tourism.presentation.trip_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.feature_tourism.domain.entity.OpenTripEntity
import com.alvindev.traverseeid.feature_tourism.presentation.component.TripCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = ScreenRoute.TripList,
)
@Composable
fun TripListScreen(
    navigator: DestinationsNavigator
) {
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
        OpenTripEntity(
            id = 4,
            title = "Open Trip Bali",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Bali"
        ),
        OpenTripEntity(
            id = 5,
            title = "Open Trip Jogja",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Jogja"
        ),
        OpenTripEntity(
            id = 6,
            title = "Open Trip Lombok",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Lombok"
        ),
        OpenTripEntity(
            id = 7,
            title = "Open Trip Gili Trawangan",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Gili Trawangan"
        ),
        OpenTripEntity(
            id = 8,
            title = "Open Trip Gili Meno",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Gili Meno"
        ),
        OpenTripEntity(
            id = 9,
            title = "Open Trip Gili Air",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Gili Air"
        ),
        OpenTripEntity(
            id = 10,
            title = "Open Trip Gili Nanggu",
            price = 1000000,
            startDate = "12-12-2021",
            endDate = "12-12-2021",
            duration = 3,
            imageUrl = "https://picsum.photos/200/300",
            location = "Gili Nanggu"
        ),
    )
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ){
        items(openTrips){ item ->
            TripCard(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                title = item.title,
                duration = item.duration,
                location = item.location,
                price = item.price,
                startDate = item.startDate,
                endDate = item.endDate,
                imageUrl = item.imageUrl,
                onClick = {
                    navigator.navigate(ScreenRoute.TripDetails)
                }
            )
        }
    }
}