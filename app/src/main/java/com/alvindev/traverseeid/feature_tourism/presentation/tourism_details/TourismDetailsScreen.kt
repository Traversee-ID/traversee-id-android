package com.alvindev.traverseeid.feature_tourism.presentation.tourism_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.feature_tourism.presentation.component.HomeStayCard
import com.alvindev.traverseeid.feature_tourism.presentation.component.ImageSlider
import com.alvindev.traverseeid.core.presentation.component.TourismCard
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.TourismDetails
)
@Composable
fun TourismDetailsScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        ImageSlider(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(bottom = 16.dp),
            images = listOf(
                R.drawable.dummy_komodo_island,
                R.drawable.dummy_bromo,
                R.drawable.dummy_borobudur,
                R.drawable.dummy_kuta_beach,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Borobudur Temple",
                    style = Typography.h1.copy(color = MaterialTheme.colors.primaryVariant)
                )
                TraverseeRowIcon(
                    icon = Icons.Outlined.Place,
                    text = "Magelang",
                )
            }
            TraverseeRowIcon(
                icon = Icons.Outlined.Cloud,
                iconTintColor = Color.Blue,
                text = "23°C",
                textStyle = Typography.body2,
            )
        }
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        AboutTourism()
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        SectionHomeStay()
        TraverseeDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 4.dp
        )
        SectionRelevantTourism()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AboutTourism() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TraverseeSectionTitle(title = "About the Tourism")
        Text(
            text = "Candi Borobudur (Jawa: ꦕꦟ꧀ꦝꦶꦧꦫꦧꦸꦝꦸꦂ, translit. Candhi Båråbudhur) adalah sebuah candi Buddha yang terletak di Borobudur, Magelang, Jawa Tengah, Indonesia. Candi ini terletak kurang lebih 100 km di sebelah barat daya Semarang, 86 km di sebelah barat Surakarta, dan 40 km di sebelah barat laut Yogyakarta. Candi dengan banyak stupa ini didirikan oleh para penganut agama Buddha Mahayana sekitar tahun 800-an Masehi pada masa pemerintahan wangsa Syailendra. Borobudur adalah candi atau kuil Buddha terbesar di dunia, sekaligus salah satu monumen Buddha terbesar di dunia.",
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