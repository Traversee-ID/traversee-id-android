package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.core.util.digitSeparator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CampaignCard(
    modifier: Modifier = Modifier,
    title: String,
    category: String,
    startDate: String,
    endDate: String,
    place: String,
    participants: Int,
    onClick: () -> Unit = {},
    imageUrl: String? = null,
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick,
        shape = Shapes.large,
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            //body
            Row {
                imageUrl?.let{
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(Shapes.large),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        fallback =painterResource(id = R.drawable.dummy_komodo_island)
                    )
                } ?:
                Image(
                    painter = painterResource(id = R.drawable.dummy_komodo_island),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(Shapes.large),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = category,
                        style = Typography.caption,
                    )
                    Text(
                        text = title,
                        style = Typography.subtitle2,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                    TraverseeRowIcon(
                        icon = Icons.Outlined.Place,
                        text = place,
                    )
                }
            }

            //footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TraverseeRowIcon(
                    modifier = Modifier.padding(end = 8.dp),
                    icon = Icons.Default.PersonOutline,
                    text = "${participants.digitSeparator()} participants"
                )
                TraverseeRowIcon(
                    icon = Icons.Default.Schedule,
                    text = "$startDate - $endDate"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignCardPreview() {
    TraverseeTheme() {
        CampaignCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = "Share your experience at Borobudur",
            category = "Cultural",
            startDate = "May 17",
            endDate = "June 17",
            place = "Magelang",
            participants = 1000,
        )
    }
}