package com.alvindev.traverseeid.feature_sentiment.domain.mapper

import android.provider.Telephony.Mms.Sent
import androidx.compose.ui.graphics.Color
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.TraverseeGreen
import com.alvindev.traverseeid.core.theme.TraverseeRed
import com.alvindev.traverseeid.core.theme.TraverseeYellow
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity

object SentimentMapper{
    fun mapIntToSentiment(sentiment: Int): SentimentConstant {
        return when (sentiment) {
            0 -> SentimentConstant.NEGATIVE
            1 -> SentimentConstant.NEUTRAL
            2 -> SentimentConstant.POSITIVE
            else -> SentimentConstant.NEUTRAL
        }
    }
    fun mapSentimentToBackgroundColor(sentiment: SentimentConstant): Color {
        return when (sentiment) {
            SentimentConstant.POSITIVE -> TraverseeGreen.copy(alpha = 0.3f)
            SentimentConstant.NEUTRAL -> TraverseeYellow.copy(alpha = 0.3f)
            SentimentConstant.NEGATIVE -> TraverseeRed.copy(alpha = 0.3f)
        }
    }

    fun mapSentimentToColor(sentiment: SentimentConstant): Color {
        return when (sentiment) {
            SentimentConstant.POSITIVE -> TraverseeGreen
            SentimentConstant.NEUTRAL -> TraverseeYellow
            SentimentConstant.NEGATIVE -> TraverseeRed
        }
    }

    fun mapSentimentToString(sentiment: SentimentConstant): String {
        return when (sentiment) {
            SentimentConstant.POSITIVE -> "Positive"
            SentimentConstant.NEUTRAL -> "Neutral"
            SentimentConstant.NEGATIVE -> "Negative"
        }
    }

    fun mapSentimentToIcon(sentiment: SentimentConstant): Int {
        return when (sentiment) {
            SentimentConstant.POSITIVE -> R.drawable.ic_positive
            SentimentConstant.NEUTRAL -> R.drawable.ic_neutral
            SentimentConstant.NEGATIVE -> R.drawable.ic_negative
        }
    }

    fun mapSentimentResponseToSentimentEntity(name: String,sentiment: SentimentEntity): SentimentEntity{
        return sentiment.copy(name = name)
    }
}