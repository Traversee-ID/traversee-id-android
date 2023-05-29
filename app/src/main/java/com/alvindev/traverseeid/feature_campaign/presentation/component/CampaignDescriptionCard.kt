package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.presentation.component.TraverseeDivider
import com.alvindev.traverseeid.core.theme.TraverseeBlack
import com.alvindev.traverseeid.core.theme.Shapes
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun CampaignDescriptionCard(
    modifier: Modifier = Modifier,
    iniatedBy: String,
    startDate: String,
    endDate: String,
    category: String,
    participants: Int
) {
    Column(
        modifier = modifier
    ) {
        RowDescriptionItem(
            title = "Iniated By",
            value = iniatedBy
        )
        TraverseeDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        RowDescriptionItem(
            title = "Date",
            value = "$startDate - $endDate"
        )
        TraverseeDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        RowDescriptionItem(
            title = "Category",
            value = category
        )
        TraverseeDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        RowDescriptionItem(
            title = "Participants",
            value = participants.toString()
        )
    }
}

@Composable
fun RowDescriptionItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Typography.caption,
            fontWeight = FontWeight.Medium,
            color = TraverseeBlack
        )
        Text(
            text = value,
            style = Typography.caption,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            textAlign = TextAlign.End,
            color = TraverseeBlack
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignDescriptionCardPreview() {
    TraverseeTheme() {
        CampaignDescriptionCard(
            modifier = Modifier
                .clip(Shapes.large)
                .background(color = Color.White)
                .shadow(2.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            iniatedBy = "Alvin Dev",
            startDate = "12/11/2021",
            endDate = "12/12/2021",
            category = "Category",
            participants = 10
        )
    }
}