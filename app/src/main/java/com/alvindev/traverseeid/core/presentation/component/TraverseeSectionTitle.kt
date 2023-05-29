package com.alvindev.traverseeid.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvindev.traverseeid.core.theme.TraverseeTheme
import com.alvindev.traverseeid.core.theme.Typography

@Composable
fun TraverseeSectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    actionText: String? = null,
    actionOnClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        subtitle?.let {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = title,
                    style = Typography.h2
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = subtitle,
                    style = Typography.caption
                )
            }
        } ?: run {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = Typography.h2
            )
        }
        actionText?.let {
                Text(
                    modifier = Modifier.clickable { actionOnClick() },
                    text = actionText.uppercase(),
                    style = Typography.button,
                    color = MaterialTheme.colors.primaryVariant,
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    TraverseeTheme {
        TraverseeSectionTitle(
            title = "Discover Campaigns",
            actionText = "See All",
            actionOnClick = {}
        )
    }
}