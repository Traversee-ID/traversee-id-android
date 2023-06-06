package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCampaignCard(
    modifier: Modifier = Modifier,
    title: String,
    status: String,
    startDate: String,
    endDate: String,
    participants: Int,
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick,
        shape = Shapes.large,
        backgroundColor = Color.White,
    ) {
        Column {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ){
                imageUrl?.let {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = it,
                        contentDescription = "Campaign Image",
                        contentScale = ContentScale.Crop,
                        fallback =painterResource(id = R.drawable.dummy_komodo_island)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.dummy_komodo_island),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                if(status.lowercase() == "completed") {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                            .align(Alignment.BottomEnd),
                        contentDescription = stringResource(id = R.string.campaign_ended),
                        painter = painterResource(id = R.drawable.ic_completed),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$startDate - $endDate",
                        style = Typography.caption,
                    )
                    Text(
                        text = status,
                        style = Typography.caption,
                    )
                }
                Text(
                    text = title,
                    style = Typography.subtitle2,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp),
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = "Participants",
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = stringResource(id = R.string.participants, participants),
                        style = Typography.caption,
                        color = TraverseeBlack
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCampaignCardPreview() {
    TraverseeTheme() {
        MyCampaignCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = "Borobudur Temple",
            status = "completed",
            startDate = "12/12/2021",
            endDate = "12/12/2021",
            participants = 100,
            imageUrl = "https://picsum.photos/200/300"
        )
    }
}
