package com.alvindev.traverseeid.feature_sentiment.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.*
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper

@Composable
fun SentimentTag(
    modifier: Modifier = Modifier,
    sentiment: SentimentConstant,
    text: String
) {
    Box(
        modifier =  modifier
            .background(SentimentMapper.mapSentimentToBackgroundColor(sentiment), shape = Shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text =text ,
            style = Typography.caption,
            color = Black,
            fontWeight = FontWeight.W500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SentimentTagPreview() {
    SentimentTag(
        sentiment = SentimentConstant.OVERWHELMINGLY_POSITIVE,
        text = "Bersih"
    )
}