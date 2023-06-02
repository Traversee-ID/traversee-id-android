package com.alvindev.traverseeid.feature_campaign.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CampaignRowIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    description: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .size(60.dp)
                .padding(end = 4.dp),
            painter = painterResource(id = icon),
            contentDescription = title
        )
        Column{
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = title,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = description,
                style = MaterialTheme.typography.caption
            )
        }
    }
}