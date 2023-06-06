package com.alvindev.traverseeid.feature_tourism.presentation.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    images: List<String> = listOf(),
) {
    val pagerState = rememberPagerState()
    Box(modifier = modifier) {
        if(images.isNotEmpty()){
            HorizontalPager(
                pageCount = images.size,
                state = pagerState,
                key = { images[it] },
                pageSize = PageSize.Fill
            ) { index ->
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = images[index],
                    fallback = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
            
            Box(
                modifier = Modifier
                    .offset(y = -(8).dp, x = -(8).dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "${pagerState.currentPage + 1}/${images.size}",
                    style = Typography.subtitle2.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )
            }
        }else{
            Image(
                painter = painterResource(id = R.drawable.empty_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
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
               ""
            )
        )
    }
}