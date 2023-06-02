package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.util.currencyFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TripCard(
    modifier: Modifier = Modifier,
    title: String,
    duration: Int,
    location: String,
    price: Int,
    startDate: String,
    endDate: String,
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick,
        shape = Shapes.large,
        backgroundColor = Color.White,
    ) {
        Column {
            imageUrl?.let {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    model = it,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.dummy_komodo_island),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        text = title,
                        style = Typography.subtitle2,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                    TraverseeRowIcon(
                        text = "$duration Days",
                        icon = Icons.Default.Schedule,
                        iconSize = 16.dp,
                    )
                }

                TraverseeRowIcon(
                    text = location,
                    icon = Icons.Outlined.Place,
                    iconSize = 16.dp,
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        text = price.currencyFormat(),
                        style = Typography.subtitle2,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                    Text(
                        text = "$startDate - $endDate",
                        style = Typography.caption,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripCardPreview() {
    TripCard(
        title = "Borobudur",
        startDate = "May 17",
        endDate = "June 17",
        location = "Magelang",
        price = 1_200_000,
        duration = 3,
    )
}
