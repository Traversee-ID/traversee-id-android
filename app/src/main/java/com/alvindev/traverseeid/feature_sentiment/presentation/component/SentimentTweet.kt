package com.alvindev.traverseeid.feature_sentiment.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper

@Composable
fun SentimentTweet(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Alvin Avatar",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier.weight(1f),
                ){
                    Text(
                        text = "Alvin",
                        style = Typography.subtitle2
                    )
                    Text(
                        text = "@alexanderhipp",
                        style = Typography.caption
                    )
                }

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = SentimentMapper.mapSentimentToIcon(SentimentConstant.POSITIVE)),
                    contentDescription = "Sentiment Icon",
                    tint = SentimentMapper.mapSentimentToColor(SentimentConstant.POSITIVE),
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Ini pertanyaan sangat valid, mengingat tradisi Buddhisme Thai saat ini adalah Theravada, sedangkan tradisi Borobudur itu Mahayana tantrik. Memang secara sejarah, tradisi Mahayana Khmer yang mirip Borobudur (CMIIW) pernah berkembang pesat di Thailand, tapi ya sudah tidak relevan.",
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "19 Juni 2023",
                    style = Typography.caption
                )
                TraverseeRowIcon(
                    icon = Icons.Outlined.ThumbUp,
                    text = "100",
                    iconSize = 16.dp,
                )
                TraverseeRowIcon(
                    drawable = R.drawable.ic_retweet,
                    text = "2",
                    iconSize = 16.dp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SentimentTweetPreview() {
    SentimentTweet()
}