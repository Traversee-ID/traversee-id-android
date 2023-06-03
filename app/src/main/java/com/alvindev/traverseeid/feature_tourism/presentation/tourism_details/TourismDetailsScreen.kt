package com.alvindev.traverseeid.feature_tourism.presentation.tourism_details

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.feature_tourism.presentation.component.HomeStayCard
import com.alvindev.traverseeid.feature_tourism.presentation.component.ImageSlider
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.core.theme.TraverseeRed
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.TourismDetails
)
@Composable
fun TourismDetailsScreen(
    tourismItem: TourismItem? = null,
    viewModel: TourismDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        tourismItem?.let {
            viewModel.setInitialState(it)
        }
    }

    if (state.errorFavorite != null) {
        Toast.makeText(context, state.errorFavorite, Toast.LENGTH_SHORT).show()
    }

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
                style = Typography.caption,
                color = TraverseeRed,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    } else if (state.tourismDetails != null && state.tourism != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            ImageSlider(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(bottom = 16.dp),
                images = listOf(
                    state.tourism.imageUrl ?: ""
                ),
                isFavorite = state.isFavorite,
                favoriteAction = {
                    if(state.isLoadingFavorite.not()){
                        if (state.isFavorite) {
                            viewModel.removeFavoriteTourism(state.tourism.id)
                        } else {
                            viewModel.addFavoriteTourism(state.tourism.id)
                        }
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TraverseeRowIcon(
                    icon = Icons.Outlined.Place,
                    text = state.tourism.locationName ?: "-",
                )
                Text(
                    text = state.tourism.categoryName ?: "-",
                    style = Typography.caption
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = state.tourism.name ?: "-",
                style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
            )
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 4.dp
            )
            AboutTourism(
                description = state.tourismDetails.description ?: ""
            )
//        TraverseeDivider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            thickness = 4.dp
//        )
//        SectionHomeStay()
//        TraverseeDivider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            thickness = 4.dp
//        )
//        SectionRelevantTourism()
//        Spacer(modifier = Modifier.height(16.dp))
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.error_occurred),
                style = Typography.caption,
                color = TraverseeRed,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun AboutTourism(
    description: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(title = stringResource(id = R.string.about_tourism))
        Text(
            text = description,
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun SectionHomeStay() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Home Stay"
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(5) {
                HomeStayCard()
            }
        }
    }
}

@Composable
fun SectionRelevantTourism() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - 32.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Relevant Tourism"
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(5) {
                TourismCard(
                    modifier = Modifier.width(screenWidth / 2),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTourismDetailsScreen() {
    TraverseeTheme {
        TourismDetailsScreen()
    }
}