package com.alvindev.traverseeid.feature_campaign.presentation.campaign_category

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
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignCategory
import com.alvindev.traverseeid.feature_campaign.presentation.component.CategoryCard
import com.ramcosta.composedestinations.annotation.Destination
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.CampaignCategory
)
@Composable
fun CampaignCategoryScreen(
    navigator: DestinationsNavigator
) {
    val campaignCategoryList = listOf(
        CampaignCategory(
            id = 1,
            name = "All Campaign",
            image = R.drawable.dummy_komodo_island
        ),
        CampaignCategory(
            id = 2,
            name = "Ecotourism",
            image = R.drawable.dummy_kuta_beach
        ),
        CampaignCategory(
            id = 3,
            name = "Religous",
            image = R.drawable.dummy_borobudur
        ),
        CampaignCategory(
            id = 4,
            name = "Ethnic",
            image = R.drawable.dummy_bromo
        ),
        CampaignCategory(
            id = 5,
            name = "Culinary",
            image = R.drawable.dummy_kuta_beach
        ),
        CampaignCategory(
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
            items(campaignCategoryList, key = { category -> category.id }) { category ->
                CategoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navigator.navigate(CampaignListScreenDestination(name = category.name))
                        },
                    size = screenWidth / 2,
                    image = category.image,
                    contentDescription = category.name,
                    text = category.name,
                    isFullSize = true,
                )
            }
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