package com.alvindev.traverseeid.feature_tourism.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeBlack
import com.alvindev.traverseeid.core.theme.TraverseeSecondary

@Composable
fun TripIconCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    description: String
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painter,
            contentDescription = title,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = title,
            style = MaterialTheme.typography.caption.copy(
                color = TraverseeBlack,
                fontWeight = FontWeight.W600
            )
        )
        Text(
            text = description,
            style = MaterialTheme.typography.caption.copy(
                fontSize = 11.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TripIconCardPreview() {
    TripIconCard(
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = Shapes.large)
            .background(MaterialTheme.colors.surface, Shapes.large),
        painter = painterResource(id = R.drawable.ic_timer),
        title = "Weather",
        description = "Sunny"
    )
}