package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.MainActivity
import com.alvindev.traverseeid.core.presentation.component.TraverseeDropdown
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Destination(
    route = ScreenRoute.TourismList
)
@Composable
fun TourismListScreen(
    name: String? = null,
    navigator: DestinationsNavigator
) {
    name?.let {
        MainActivity.routeName = it
    }
    val dropdownMenuItems = listOf("Indonesia", "Magelang", "Depok", "Semarang", "Purworkerto")
    val selectedItem = remember { mutableStateOf(dropdownMenuItems[0]) }

    LazyColumn {
        item {
            TraverseeDropdown(
                dropdownMenuItems = dropdownMenuItems,
                selectedItem = selectedItem.value,
                onSelectedItemChange = { selectedItem.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        items(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).chunked(2)) {
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

@Preview(showBackground = true)
@Composable
fun PreviewTourismListScreen() {
    TourismListScreen(
        navigator = EmptyDestinationsNavigator
    )
}