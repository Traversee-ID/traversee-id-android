package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun TraverseeCategoryCard(
    modifier: Modifier = Modifier,
    image: String? = null,
    contentDescription: String,
    text: String,
    isFullSize: Boolean = false,
) {
    val allCampaigns = stringResource(id = R.string.all_campaigns)
    val allPlaces = stringResource(id = R.string.all_places)
    val isAllCategory = text == allCampaigns || text == allPlaces

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isAllCategory) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(Shapes.large),
                painter = painterResource(id = R.drawable.dummy_komodo_island),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(Shapes.large),
                model = image,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                fallback = painterResource(id = R.drawable.dummy_komodo_island)
            )
        }
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = if (isFullSize) Typography.subtitle1 else Typography.subtitle2,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    TraverseeTheme() {
        TraverseeCategoryCard(
            image = "",
            contentDescription = "Komodo island",
            text = "All Campaign"
        )
    }
}