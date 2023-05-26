package com.alvindev.traverseeid.feature_sentiment.domain.mapper

import androidx.compose.ui.graphics.Color
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeGreen
import com.alvindev.traverseeid.core.theme.TraverseeRed
import com.alvindev.traverseeid.core.theme.TraverseeYellow
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant

object SentimentMapper{
    fun mapSentimentToBackgroundColor(sentiment: SentimentConstant): Color {
        return when (sentiment) {
            SentimentConstant.OVERWHELMINGLY_POSITIVE -> TraverseeGreen.copy(alpha = 0.4f)
            SentimentConstant.POSITIVE -> TraverseeGreen.copy(alpha = 0.3f)
            SentimentConstant.NEUTRAL -> TraverseeYellow.copy(alpha = 0.3f)
            SentimentConstant.NEGATIVE -> TraverseeRed.copy(alpha = 0.3f)
            SentimentConstant.OVERWHELMINGLY_NEGATIVE -> TraverseeRed.copy(alpha = 0.4f)
            else -> Color.Transparent
        }
    }

    fun mapSentimentToColor(sentiment: SentimentConstant): Color {
        return when (sentiment) {
            SentimentConstant.OVERWHELMINGLY_POSITIVE -> TraverseeGreen
            SentimentConstant.POSITIVE -> TraverseeGreen
            SentimentConstant.NEUTRAL -> TraverseeYellow
            SentimentConstant.NEGATIVE -> TraverseeRed
            SentimentConstant.OVERWHELMINGLY_NEGATIVE -> TraverseeRed
            else -> Color.Transparent
        }
    }

    fun mapSentimentToString(sentiment: SentimentConstant): String {
        return when (sentiment) {
            SentimentConstant.OVERWHELMINGLY_POSITIVE -> "Overwhelmingly Positive"
            SentimentConstant.POSITIVE -> "Positive"
            SentimentConstant.NEUTRAL -> "Neutral"
            SentimentConstant.NEGATIVE -> "Negative"
            SentimentConstant.OVERWHELMINGLY_NEGATIVE -> "Overwhelmingly Negative"
            else -> ""
        }
    }

    fun mapSentimentToIcon(sentiment: SentimentConstant): Int {
        return when (sentiment) {
            SentimentConstant.OVERWHELMINGLY_POSITIVE -> R.drawable.ic_positive
            SentimentConstant.POSITIVE -> R.drawable.ic_positive
            SentimentConstant.NEUTRAL -> R.drawable.ic_neutral
            SentimentConstant.NEGATIVE -> R.drawable.ic_negative
            SentimentConstant.OVERWHELMINGLY_NEGATIVE -> R.drawable.ic_negative
            else -> R.drawable.ic_neutral
        }
    }
}