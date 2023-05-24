package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.feature_campaign.domain.constant.CampaignParticipantConstant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCampaignCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    status: String = "Ongoing",
    startDate: String = "May 17",
    endDate: String = "June 17",
    participants: Int = 1000,
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
                Image(
                    painter = painterResource(id = R.drawable.dummy_borobudur),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                if(status == "Ended") {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .size(24.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(50))
                            .padding(2.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = "Campaign Ended",
                        tint = Color.Green,
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
                    color = MaterialTheme.colors.primaryVariant,
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
                        tint = Purple500
                    )
                    Text(
                        text = "$participants participants",
                        style = Typography.caption,
                        color = Black
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
        )
    }
}
