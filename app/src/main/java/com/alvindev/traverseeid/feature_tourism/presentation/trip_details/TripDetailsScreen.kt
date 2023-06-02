package com.alvindev.traverseeid.feature_tourism.presentation.trip_details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.*
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_tourism.presentation.component.ImageSlider
import com.alvindev.traverseeid.feature_tourism.presentation.component.TripIconCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = ScreenRoute.TripDetails,
)
@Composable
fun TripDetailsScreen(
    navigator: DestinationsNavigator,
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 56.dp),
        ) {
            ImageSlider(
                modifier = Modifier
                    .aspectRatio(1f),
                images = listOf(
                    R.drawable.dummy_komodo_island,
                    R.drawable.dummy_bromo,
                    R.drawable.dummy_borobudur,
                    R.drawable.dummy_kuta_beach,
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Borobudur Temple",
                    style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rp1.000.000",
                        style = MaterialTheme.typography.subtitle2
                    )
                    TraverseeRowIcon(
                        icon = Icons.Outlined.Place,
                        text = "Magelang",
                        iconSize = 16.dp,
                    )
                }
            }

            ScheduleTrip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )

            AboutTrip()
            TraverseeDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 4.dp
            )
            SectionTripDestination(
                destinationOnClick = {
                    navigator.navigate(ScreenRoute.TourismDetails)
                }
            )
        }

        TraverseeButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp))
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = {}
        ){
            TraverseeRowIcon(
                modifier = Modifier.fillMaxWidth(),
                text = "Book via WhatsApp",
                icon = Icons.Outlined.Whatsapp,
                textColor = Color.White,
                iconTintColor = Color.White,
                iconPaddingEnd = 8.dp,
                horizontalArrangement = Arrangement.Center,
            )
        }
    }
}

@Composable
fun SectionTripDestination(
    destinationOnClick: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - (16.dp * 2)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(10) {
            TourismCard(
                modifier = Modifier
                    .width(screenWidth / 2)
                    .clickable{
                        destinationOnClick()
                    },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScheduleTrip(
    modifier: Modifier = Modifier,
) {
    val scheduleTitle = listOf(
        "Schedule",
        "Duration",
        "Meeting Point",
        "Destination",
    )
    val scheduleDescription = listOf(
        "20 November 2021",
        "2 Hari 1 Malam",
        "Stasiun Gambir",
        "Borobudur Temple",
    )
    val scheduleIcons = listOf(
        Icons.Outlined.DateRange,
        Icons.Outlined.Schedule,
        Icons.Outlined.Place,
        Icons.Outlined.Flight,
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp - (16.dp * 2)

    FlowRow(
        modifier = modifier,
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
                description = scheduleDescription[index],
            )
        }
    }
}

@Composable
fun AboutTrip() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(title = "About the Trip")
        Text(
            text = "Wisata Tur Situ Gunung Sukabumi Jawa Barat merupakan salah satu destinasi wisata alam di Indonesia yang menarik untuk dikunjung. Wisata yang terletak di Kadudampit, Sukabumi, Jawa Barat ini merupakan kawasan yang menyajikan beragam objek wisata alam menarik. Seperti area berkemah yang luas dan sejuk hingga jembatan gantung yang ikonik. Meskipun termasuk wisata yang murah dan terjangkau, Anda tidak akan kecewa dengan suguhan pemandangan alam dan berbagai fasilitas yang bisa dinikmati. Dengan begitu, Anda bisa mengurangi rasa stres dan penat dari padatnya aktivitas harian.\n" +
                    "\n" +
                    "WIsata Tur Situ Gunung Sukabumi merupakan kawasan wisata alam yang menyajikan beragam objek menarik bagi pengunjung di Jawa Barat. Mulai dari jembatan gantung, area berkemah atau camping, danau dengan air jernih, hingga air terjun yang sejuk dan asri. Kawasan wisata ini terletak di Kadudampit, Sukabumi, Jawa Barat. Selain beberapa objek wisata menarik yang bisa dikunjungi, Anda juga bisa menikmati beberapa fasilitas dan layanan yang tak kalah menarik. Seperti fasilitas WiFi gratis, cafeteria yang menyajikan beragam makanan, toilet, dan mushola bagi pengunjung yang hendak beribadah. Selain itu, Situ Gunung Sukabumi juga memberikan fasilitas api unggun bagi para wisatawan yang bermalam atau berkemah. Ada pula trip rakit yang bisa memberikan pengalaman dan petualangan seru bagi Anda dan keluarga.\n" +
                    "\n" +
                    "Ayo gabung di Open Trip Situ Gunung Sukabumi sekarang.",
            style = Typography.body2,
            textAlign = TextAlign.Justify,
        )
    }
}