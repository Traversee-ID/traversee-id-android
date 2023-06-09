package com.alvindev.traverseeid.feature_campaign.presentation.component

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeOutlinedButton
import com.alvindev.traverseeid.core.theme.*

@Composable
fun CampaignParticipantItem(
    modifier: Modifier = Modifier,
    winnerName: String,
    winnerPhoto: String? = null,
    winnerRank: Int = -1,
    winnerSubmission: String,
) {
    val context = LocalContext.current
    val submissionUrl = if(winnerSubmission.startsWith("http")) winnerSubmission else "https://$winnerSubmission"
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(submissionUrl))

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        winnerPhoto?.let {
            AsyncImage(
                model = it,
                contentDescription = winnerName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = RoundedCornerShape(50)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } ?: Image(
            painterResource(id = R.drawable.ic_profile),
            contentDescription = winnerName,
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
                    val color = when(winnerRank){
                        1 -> TraverseeGold
                        2 -> TraverseeSilver
                        3 -> TraverseeBronze
                        else -> MaterialTheme.colors.onSurface
                    }
                    Text(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(50))
                            .background(color = color)
                            .wrapContentSize(Alignment.Center),
                        text = "#$winnerRank",
                        style = Typography.subtitle2,
                    )
                }
            }

            TraverseeOutlinedButton(
                text = stringResource(id = R.string.see_submission),
                onClick = {
                    context.startActivity(webIntent)
                },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinnerCampaignPreview() {
    TraverseeTheme() {
        CampaignParticipantItem(
            winnerName = "Alvin",
            winnerPhoto = "",
            winnerRank = 1,
            winnerSubmission = "https://www.google.com"
        )
    }
}