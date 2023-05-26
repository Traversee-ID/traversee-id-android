package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun TourismCard(
    modifier: Modifier = Modifier,
    title: String = "Boroboudur Temple",
    category: String = "Religious",
    city: String = "Magelang",
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(Shapes.large),
            painter = painterResource(id = R.drawable.dummy_komodo_island),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(top= 8.dp , bottom = 2.dp),
            text = category,
            style = Typography.caption,
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = title,
            style = Typography.subtitle2,
        )
        TraverseeRowIcon(
            icon = Icons.Outlined.Place,
            iconSize = 16.dp,
            text = city,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTourismCard() {
    TraverseeTheme {
        TourismCard()
    }
}

