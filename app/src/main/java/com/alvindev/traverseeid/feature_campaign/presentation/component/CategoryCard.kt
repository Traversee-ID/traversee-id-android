package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    image: Int,
    contentDescription: String,
    text: String,
    isFullSize: Boolean = false,
    size: Dp = 100.dp
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(size).clip(Shapes.large),
            painter = painterResource(id = image),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = text,
            style = if(isFullSize) Typography.subtitle1 else Typography.subtitle2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview(){
    TraverseeTheme() {
        CategoryCard(
            image = R.drawable.dummy_komodo_island,
            contentDescription = "Komodo island",
            text = "All Campaign"
        )
    }
}