package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    status: String,
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick,
        shape = Shapes.large,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
            ) {
                imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        fallback = painterResource(id = R.drawable.dummy_komodo_island)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.dummy_komodo_island),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "$startDate - $endDate",
                        style = Typography.caption.copy(
                            fontSize = 11.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W500
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if(status.lowercase() == "completed") {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                            .align(Alignment.BottomStart),
                        contentDescription = stringResource(id = R.string.campaign_ended),
                        painter = painterResource(id = R.drawable.ic_completed),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = category,
                    style = Typography.caption.copy(
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = FontWeight.W600
                    ),
                )
                Text(
                    text = title,
                    style = Typography.subtitle1.copy(
                        fontWeight = FontWeight.W700
                    ),
                )
                TraverseeRowIcon(
                    icon = Icons.Outlined.Place,
                    text = place,
                )
                TraverseeRowIcon(
                    modifier = Modifier.padding(end = 8.dp),
                    icon = Icons.Default.PersonOutline,
                    text = stringResource(id = R.string.participants, participants.digitSeparator()),
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
                .fillMaxWidth(),
            title = "Share your experience at Borobudur",
            category = "Cultural",
            startDate = "May 17",
            endDate = "June 17",
            place = "Magelang",
            participants = 1000,
            status = "completed",
        )
    }
}