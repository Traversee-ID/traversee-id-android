package com.alvindev.traverseeid.feature_sentiment.presentation.sentiment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.presentation.component.TraverseeTextField
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.presentation.component.TraverseeSectionTitle
import com.alvindev.traverseeid.feature_sentiment.domain.constant.SentimentConstant
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper
import com.alvindev.traverseeid.feature_sentiment.presentation.component.SentimentTag
import com.alvindev.traverseeid.feature_sentiment.presentation.component.SentimentItem
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeErrorState
import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity
import com.alvindev.traverseeid.navigation.ScreenRoute
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalComposeUiApi::class)
@Destination(
    route = ScreenRoute.Sentiment,
)
@Composable
fun SentimentScreen(
    viewModel: SentimentViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_analysis))

    LazyColumn {
        item {
            TraverseeSectionTitle(
                modifier = Modifier.padding(16.dp),
                title = stringResource(id = R.string.sentiment_tourism),
                subtitle = stringResource(id = R.string.discouver_favorite_tourism),
            )
        }
        item {
            TraverseeTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .focusRequester(focusRequester = focusRequester),
                label = {
                    Text(stringResource(id = R.string.search_tourism))
                },
                placeholder = {
                    Text(stringResource(id = R.string.search_tourism))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                onValueChange = viewModel::onQueryChanged,
                value = state.query,
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.onSubmitQuery()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        }
        if (state.isLoading) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxHeight(0.7f).fillParentMaxWidth().padding(horizontal=32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )
                }
            }
        } else if (state.error != null) {
            item {
                TraverseeErrorState(
                    modifier = Modifier.fillParentMaxHeight(0.7f).fillParentMaxWidth(),
                    image = painterResource(id = if(state.error.contains("not available")) R.drawable.empty_list else R.drawable.empty_error),
                    title = if(state.error.contains("not available")) state.error else stringResource(id = R.string.error_title),
                    description = if(state.error.contains("not available")) null else stringResource(id = R.string.error_description),
                    isCanRetry = !state.error.contains("not available"),
                    onRetry = {
                        viewModel.onSubmitQuery(retry=true)
                    }
                )
            }
        } else {
            item {
               if(state.queryResult.isNotBlank()){
                   Text(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                       text = stringResource(id = R.string.sentiment_result, "\"${state.queryResult}\""),
                       style = Typography.h2,
                       textAlign = TextAlign.Center
                   )
               }
            }
//            item {
//                SentimentResultCard()
//            }
//            item {
//                Spacer(modifier = Modifier.height(24.dp))
//            }
            item {
                state.sentimentData?.let {
                    SentimentItemCard(it)
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentimentResultCard() {
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
            .shadow(4.dp, shape = Shapes.large)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = SentimentMapper.mapSentimentToString(SentimentConstant.POSITIVE),
            style = Typography.subtitle1,
            color = SentimentMapper.mapSentimentToColor(SentimentConstant.POSITIVE)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            commonWords.forEachIndexed { index, word ->
                SentimentTag(
                    modifier = Modifier.padding(bottom = 8.dp),
                    sentiment = if (index == commonWords.size - 1) SentimentConstant.NEGATIVE else SentimentConstant.POSITIVE,
                    text = word
                )
            }
        }
    }
}

@Composable
fun SentimentItemCard(data: List<SentimentEntity>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = Shapes.large)
            .fillMaxWidth()
            .background(color = Color.White, shape = Shapes.large)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.user_reviews),
            style = Typography.subtitle1,
        )

        data.forEachIndexed { index, it ->
            Column {
                SentimentItem(
                    modifier = Modifier.padding(vertical = 8.dp),
                    sentiment = it.sentimen ?: 1,
                    name = it.name ?: "-",
                    imageUrl = it.gambar,
                    content = it.isi ?: "-",
                    rating = it.rating ?: 0,
                    likes = it.like ?: "0",
                )
                if (index != data.size - 1) {
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