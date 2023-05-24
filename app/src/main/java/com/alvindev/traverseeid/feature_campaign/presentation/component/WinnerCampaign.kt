package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeOutlinedButton
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun CampaignWinnerItem(
    modifier: Modifier = Modifier,
    winnerName: String,
    winnerPhoto: String,
    winnerRank: Int = -1,
    winnerSubmission: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.dummy_kuta_beach),
            contentDescription = "Winner Photo",
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(50)),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = winnerName,
                    style = Typography.subtitle2
                )
                if(winnerRank != -1){
                    Text(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(50))
                            .background(color = MaterialTheme.colors.secondary)
                            .wrapContentSize(Alignment.Center),
                        text = "#$winnerRank",
                        style = Typography.subtitle2,
                    )
                }
            }

            TraverseeOutlinedButton(
                text = "See Submission",
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinnerCampaignPreview() {
    TraverseeTheme() {
        CampaignWinnerItem(
            winnerName = "Alvin",
            winnerPhoto = "",
            winnerRank = 1,
            winnerSubmission = "https://www.google.com"
        )
    }
}