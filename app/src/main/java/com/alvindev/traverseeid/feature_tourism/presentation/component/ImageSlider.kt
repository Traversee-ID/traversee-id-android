package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    images: List<Int> = listOf(),
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState,
            key = { images[it] },
            pageSize = PageSize.Fill
        ) { index ->
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = images[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier
                .offset(y = -(8).dp, x = (-8).dp)
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(8.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text(
                text = "${pagerState.currentPage + 1}/${images.size}",
                style = Typography.subtitle2.copy(color = Color.White),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageSliderPreview() {
    TraverseeTheme {
        ImageSlider(
            modifier = Modifier.aspectRatio(1f),
            images = listOf(
                R.drawable.dummy_komodo_island,
                R.drawable.dummy_bromo,
                R.drawable.dummy_borobudur,
                R.drawable.dummy_kuta_beach,
            )
        )
    }
}