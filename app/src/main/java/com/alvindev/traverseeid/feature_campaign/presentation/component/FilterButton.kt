package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeRowIcon
import com.alvindev.traverseeid.core.theme.TraverseeBlack200
import com.alvindev.traverseeid.core.theme.TraverseeTheme

@Composable
fun FilterButton(
    modifier: Modifier,
    onClickFilter: () -> Unit = {},
) {
    Box(
        modifier = modifier,
    ) {
        TraverseeRowIcon(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topEndPercent = 50,
                        bottomEndPercent = 50
                    )
                )
                .clickable { onClickFilter() },
            icon = Icons.Default.FilterList,
            text = "Filter",
            textStyle = MaterialTheme.typography.body2,
            horizontalArrangement = Arrangement.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterSortButtonPreview() {
    TraverseeTheme() {
        FilterButton(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .paddingFromBaseline(16.dp)
                .clip(RoundedCornerShape(50))
                .background(color = Color.White)
                .shadow(2.dp)
                .padding(16.dp)
        )
    }
}