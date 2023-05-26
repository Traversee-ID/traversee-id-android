package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun HomeStayCard(
    modifier: Modifier = Modifier,
    title: String = "Stay with Bobby",
    price: Int = 400000,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(1.1f)
                .clip(Shapes.large),
            painter = painterResource(id = R.drawable.dummy_komodo_island),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = title,
            style = Typography.subtitle2,
        )
        Text(
            text = "Rp.$price/night",
            style = Typography.caption,
        )
    }
}