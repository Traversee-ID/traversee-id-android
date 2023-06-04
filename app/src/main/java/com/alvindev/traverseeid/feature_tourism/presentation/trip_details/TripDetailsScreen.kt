package com.alvindev.traverseeid.feature_tourism.presentation.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alvindev.destinations.TourismDetailsScreenDestination
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.core.util.digitSeparator
import com.alvindev.traverseeid.core.util.toDate
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity
import com.alvindev.traverseeid.feature_tourism.presentation.component.ImageSlider
import com.alvindev.traverseeid.feature_tourism.presentation.component.TripIconCard
import com.alvindev.traverseeid.feature_tourism.util.TourismUtil
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(
    route = ScreenRoute.TripDetails,
)
@Composable
fun TripDetailsScreen(
    navigator: DestinationsNavigator,
    trip: TripEntity? = null,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit){
        if(trip !== state.trip && trip != null){
            viewModel.setInitialData(trip)
        }
    }

    if(state.trip != null){
        val phoneNumber = state.trip.phoneNumber ?: ""
        val message = stringResource(
            id = R.string.book_via_whatsapp_message,
            state.trip.organizer ?: "-",
            state.trip.title ?: "-"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 64.dp),
            ) {
                ImageSlider(
                    modifier = Modifier
                        .aspectRatio(1f),
                    images = state.trip.imagesUrl,
                    isTourism = false,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = state.trip.title ?: "-",
                        style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.trip.price ?: "-",
                            style = MaterialTheme.typography.subtitle2
                        )

                        Text(
                            text = state.trip.categories.joinToString(separator = ", "),
                            style = MaterialTheme.typography.caption,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom=16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.organizer) + ":",
                        style = Typography.subtitle2,
                        color = TraverseeBlack
                    )
                    Text(
                        text = state.trip.organizer ?: "-",
                        style = Typography.subtitle2,
                        modifier = Modifier
                            .padding(start = 4.dp),
                        color = TraverseeSecondaryVariant
                    )
                }

                ScheduleTrip(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    schedule = "${state.trip.tripStart.toDate()} - ${state.trip.tripEnd.toDate()}",
                    duration = state.trip.duration ?: "-",
                    deadline = state.trip.regisDeadline.toDate(),
                    destination = state.destinations.size.digitSeparator(),
                )
                AboutTrip(
                    state.trip.description ?: "-"
                )
                if(state.destinations.isNotEmpty()){
                    TraverseeDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        thickness = 4.dp
                    )
                    SectionTripDestination(
                        destinations = state.destinations,
                        destinationOnClick = {
                            navigator.navigate(TourismDetailsScreenDestination(id = it.id))
                        }
                    )
                }
            }

            TraverseeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp))
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onClick = {
                    if(phoneNumber.isNotEmpty()){
                        TourismUtil.sendViaWhatsApp(
                            context = context,
                            phoneNumber = phoneNumber,
                            message = message,
                        )
                    }
                }
            ) {
                TraverseeRowIcon(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.book_via_whatsapp),
                    icon = Icons.Outlined.Whatsapp,
                    textColor = Color.White,
                    iconTintColor = Color.White,
                    iconPaddingEnd = 8.dp,
                    horizontalArrangement = Arrangement.Center,
                )
            }
        }
    } else if(state.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = state.error ?: stringResource(id = R.string.error_occurred),
                style = Typography.caption,
                textAlign = TextAlign.Center,
                color = TraverseeRed
            )
        }
    }
}

@Composable
fun SectionTripDestination(
    destinationOnClick: (tourism: TourismEntity) -> Unit = {},
    destinations: List<TourismEntity> = emptyList(),
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - (16.dp * 2)
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(id = R.string.trip_destination)
        )
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(destinations) { item ->
                TourismCard(
                    modifier = Modifier
                        .width(screenWidth / 2),
                    title = item.name ?: "-",
                    category = item.categoryName ?: "-",
                    city = item.locationName ?: "-",
                    imageUrl = item.imageUrl,
                    onClick = {
                        destinationOnClick(item)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScheduleTrip(
    modifier: Modifier = Modifier,
    schedule : String,
    duration : String,
    deadline : String,
    destination : String,
) {
    val scheduleTitle = listOf(
        stringResource(id = R.string.schedule),
        stringResource(id = R.string.duration),
        stringResource(id = R.string.deadline),
        stringResource(id = R.string.destination),
    )
    val scheduleIcons = listOf(
        Icons.Outlined.DateRange,
        Icons.Outlined.Schedule,
        Icons.Outlined.HourglassTop,
        Icons.Outlined.Flight,
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - (16.dp * 2)
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        scheduleIcons.forEachIndexed { index, icon ->
            TripIconCard(
                modifier = Modifier
                    .width(screenWidth / 2)
                    .height(100.dp)
                    .padding(4.dp)
                    .shadow(2.dp, shape = Shapes.large)
                    .background(MaterialTheme.colors.surface, Shapes.large),
                icon = icon,
                title = scheduleTitle[index],
                description = when(index){
                    0 -> schedule
                    1 -> duration
                    2 -> deadline
                    3 -> destination
                    else -> ""
                },
            )
        }
    }
}

@Composable
fun AboutTrip(
    description: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(title = stringResource(id = R.string.about_trip))
        Text(
            text = description,
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTripDetails() {
    TraverseeTheme {
        TripDetailsScreen(
            navigator = EmptyDestinationsNavigator
        )
    }
}