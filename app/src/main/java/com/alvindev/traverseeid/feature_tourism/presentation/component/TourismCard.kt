package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TourismCard(
    modifier: Modifier = Modifier,
    title: String = "Boroboudur Temple",
    category: String = "Religious",
    city: String = "Magelang",
    imageUrl: String? = null,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        onClick = onClick,
        shape = Shapes.medium,
        backgroundColor = Color.White,
    ) {
        Column{
            imageUrl?.let {
                val finalImageUrl = if(it.startsWith("http://")) it.replace("http://", "https://") else it
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                    model = finalImageUrl,
                    fallback = painterResource(id = R.drawable.dummy_komodo_island),
                    contentDescription = title,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            } ?: Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                painter = painterResource(id = R.drawable.dummy_komodo_island),
                contentDescription = title,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(top= 8.dp , bottom = 2.dp,start= 8.dp, end = 8.dp),
                text = category,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W600, color = MaterialTheme.colors.secondaryVariant),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp,start= 8.dp, end = 8.dp),
                text = title,
                style = Typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            TraverseeRowIcon(
                modifier = Modifier.padding(bottom = 8.dp,start= 8.dp, end = 8.dp),
                icon = Icons.Outlined.Place,
                iconSize = 16.dp,
                text = city,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTourismCard() {
    TraverseeTheme {
        TourismCard(
            modifier = Modifier.height(320.dp),
            title = "DASDsadoaisjkdhasdjkhasjkdash kjd saldjasdlkasd asldkjs"
        )
    }
}

