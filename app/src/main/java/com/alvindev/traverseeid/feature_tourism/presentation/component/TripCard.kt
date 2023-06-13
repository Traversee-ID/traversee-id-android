package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.Typography
import com.alvindev.traverseeid.core.util.currencyFormat
import com.alvindev.traverseeid.core.util.toDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TripCard(
    modifier: Modifier = Modifier,
    title: String,
    duration: String,
    categories: String,
    price: String,
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
                painter = painterResource(id = R.drawable.empty_image),
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
                    )
                    TraverseeRowIcon(
                        text = duration,
                        icon = Icons.Default.Schedule,
                        iconSize = 16.dp,
                    )
                }

                if(categories.isNotEmpty()){
                    Text(
                        text = categories,
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W600, color = MaterialTheme.colors.secondaryVariant),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        text = price.currencyFormat(),
                        style = Typography.subtitle2,
                    )
                    Text(
                        text = "${startDate.toDate()} - ${endDate.toDate()}",
                        style = Typography.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
        categories = "Magelang",
        price = "Rp. 1.000.000",
        duration = "3 Days",
    )
}
