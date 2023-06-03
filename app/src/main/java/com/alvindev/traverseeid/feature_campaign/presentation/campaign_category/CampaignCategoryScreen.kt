package com.alvindev.traverseeid.feature_campaign.presentation.campaign_category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.destinations.CampaignListScreenDestination
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.presentation.component.TraverseeCategoryCard
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.CampaignCategory
)
@Composable
fun CampaignCategoryScreen(
    navigator: DestinationsNavigator,
    campaignCategories: ArrayList<CategoryEntity> = arrayListOf()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(campaignCategories, key = { category -> category.id }) { category ->
            TraverseeCategoryCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(
                            CampaignListScreenDestination(
                                id = category.id,
                                name = category.name ?: ""
                            )
                        )
                    },
                image = category.imageUrl ?: "",
                contentDescription = category.name ?: "",
                text = category.name ?: "",
                isFullSize = true,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignCategoryScreenPreview() {
    TraverseeTheme() {
        CampaignCategoryScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}