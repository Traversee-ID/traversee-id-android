package com.alvindev.traverseeid.feature_sentiment.presentation.sentiment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper
import com.alvindev.traverseeid.feature_sentiment.presentation.component.SentimentTag
import com.alvindev.traverseeid.feature_sentiment.presentation.component.SentimentTweet
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route = ScreenRoute.Sentiment,
)
@Composable
fun SentimentScreen() {
    LazyColumn {
        item{
            TraverseeSectionTitle(
                modifier = Modifier.padding(16.dp),
                title = stringResource(id = R.string.sentiment_tourism),
                subtitle = stringResource(id = R.string.discouver_favorite_tourism),
            )
        }
        item{
            TraverseeTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                label = {
                    Text(stringResource(id = R.string.search_tourism))
                },
                placeholder = {
                    Text("Borobudur")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            )
        }
        item{
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                text = stringResource(id = R.string.sentiment_result, "Borobudur"),
                style = Typography.h2,
                textAlign = TextAlign.Center
            )
        }
        item{
            SentimentResultCard()
        }
        item{
            Spacer(modifier = Modifier.height(24.dp))
        }
        item{
            SentimentTweetCard()
        }
        item{
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentimentResultCard(){
    val commonWords = listOf(
        "Beautiful",
        "Mengesankan!",
        "Amazing",
        "Indah",
        "Wonderful",
        "Kotor"
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = Shapes.large,)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = SentimentMapper.mapSentimentToString(SentimentConstant.OVERWHELMINGLY_POSITIVE),
            style = Typography.subtitle1,
            color = SentimentMapper.mapSentimentToColor(SentimentConstant.OVERWHELMINGLY_POSITIVE)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            commonWords.forEachIndexed { index, word ->
                SentimentTag(
                    modifier = Modifier.padding(bottom = 8.dp),
                    sentiment = if(index == commonWords.size - 1) SentimentConstant.OVERWHELMINGLY_NEGATIVE else SentimentConstant.OVERWHELMINGLY_POSITIVE,
                    text = word
                )
            }
        }
    }
}

@Composable
fun SentimentTweetCard(){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = Shapes.large)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.relevant_tweets),
            style = Typography.subtitle1,
        )

        for(i in 0..5) {
            Column {
                SentimentTweet()
                if(i != 5){
                    TraverseeDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SentimentScreenPreview() {
    SentimentScreen(
    )
}