package com.alvindev.traverseeid.feature_tourism.presentation.tourism_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.ForumPostScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_tourism.presentation.component.HomeStayCard
import com.alvindev.traverseeid.feature_tourism.presentation.component.ImageSlider
import com.alvindev.traverseeid.core.theme.TraverseeBlack
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.TourismDetails
)
@Composable
fun TourismDetailsScreen(
    navigator: DestinationsNavigator,
    id: String? = null,
    tourismItem: TourismItem? = null,
    viewModel: TourismDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (state.tourism == null) {
            tourismItem?.let {
                viewModel.setInitialState(it)
            } ?: id?.let {
                viewModel.setInitialStateWithId(it)
            }
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
        TraverseeErrorState(
            modifier = Modifier.fillMaxSize(),
            image = painterResource(id = R.drawable.empty_error),
            title = stringResource(id = R.string.error_title),
            description = stringResource(id = R.string.error_description),
            isCanRetry = true,
            onRetry = {
                tourismItem?.let {
                    viewModel.setInitialState(it)
                } ?: id?.let {
                    viewModel.setInitialStateWithId(it)
                }
            }
        )
    } else if (state.tourismDetails != null && state.tourism != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(bottom = 16.dp)
                ) {
                    ImageSlider(
                        modifier = Modifier
                            .fillMaxSize(),
                        images = listOf(
                            state.tourism.imageUrl ?: ""
                        ),
                    )

                    //background color
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        TraverseeBlack.copy(alpha = 0.5f),
                                        TraverseeBlack.copy(alpha = 0.1f)
                                    ),
                                )
                            )
                    )
                }

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
                Spacer(modifier = Modifier.height(24.dp))
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


            // top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TraverseeBlack.copy(alpha = 0.5f))
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        navigator.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color.White
                    )
                }


                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.tourism_details),
                    textAlign = TextAlign.Center,
                    style = Typography.h6,
                    color = Color.White
                )

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        if (state.isFavorite) {
                            viewModel.removeFavoriteTourism(state.tourism.id)
                        } else {
                            viewModel.addFavoriteTourism(state.tourism.id)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "share",
                        tint = Color.White
                    )
                }
            }
        }
    } else {
        TraverseeErrorState(
            modifier = Modifier
                .fillMaxSize(),
            image = painterResource(id = R.drawable.empty_error),
            title = stringResource(id = R.string.error_title),
            description = stringResource(id = R.string.error_description),
            isCanRetry = true,
            onRetry = {
                tourismItem?.let {
                    viewModel.setInitialState(it)
                } ?: id?.let {
                    viewModel.setInitialStateWithId(it)
                }
            }
        )
    }
}

@Composable
fun AboutTourism(
    description: String
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
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
        TourismDetailsScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}