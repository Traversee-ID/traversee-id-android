package com.alvindev.traverseeid.feature_tourism.presentation.tourism_category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.destinations.TourismListScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.core.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.TourismCategory
)
@Composable
fun TourismCategoryScreen(
    categories: ArrayList<CategoryEntity>? = null,
    navigator: DestinationsNavigator
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    categories?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(categories, key = { category -> category.id }) { category ->
                    TraverseeCategoryCard(
                        modifier = Modifier
                            .width(screenWidth)
                            .padding(8.dp)
                            .clickable {
                                navigator.navigate(
                                    TourismListScreenDestination(
                                        id = category.id,
                                        name = category.name
                                    )
                                )
                            },
                        image = category.imageUrl,
                        contentDescription = category.name ?: "-",
                        text = category.name ?: "",
                        isFullSize = true,
                    )
                }
            }
        }
    } ?: Column(
        modifier = Modifier
            .fillMaxSize(),
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

@Preview(showBackground = true)
@Composable
fun TourismPlaceScreenPreview() {
    TraverseeTheme() {
        TourismCategoryScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}