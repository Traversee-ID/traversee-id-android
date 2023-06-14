package com.alvindev.traverseeid.feature_sentiment.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeYellow
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.util.digitSeparator
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper

@Composable
fun SentimentItem(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String? = null,
    sentiment: Int,
    content: String,
    rating: Int,
    likes: String,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        imageUrl?.let {
            AsyncImage(
                model = it,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50)),
                fallback = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        } ?: Image(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Avatar",
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
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    style = Typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        id = SentimentMapper.mapSentimentToIcon(
                            SentimentMapper.mapIntToSentiment(sentiment)
                        )
                    ),
                    contentDescription = "Sentiment Icon",
                    tint = SentimentMapper.mapSentimentToColor(SentimentMapper.mapIntToSentiment(sentiment)),
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = content,
                style = Typography.body2,
                textAlign = TextAlign.Justify,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TraverseeRowIcon(
                    icon = Icons.Outlined.ThumbUp,
                    text = likes,
                    iconSize = 24.dp,
                    textStyle = MaterialTheme.typography.subtitle2
                )
                TraverseeRowIcon(
                    icon= Icons.Filled.Star,
                    text = rating.toString(),
                    iconSize = 24.dp,
                    iconTintColor = TraverseeYellow,
                    textStyle = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SentimentTweetPreview() {
    SentimentItem(
        name = "Alvin Dev",
        sentiment = 1,
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nunc nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies aliquam, nunc nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
        rating = 5,
        likes = "100",
    )
}